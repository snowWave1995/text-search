package com.snowwave.textsearch.controller;
import com.snowwave.textsearch.service.FileService;
import com.snowwave.textsearch.util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangfuqiang on 2018/1/25.
 */
@Controller
@Slf4j
public class FileController {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping(path = {"/uploadFile/"}, method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
              String fileUrl = fileService.saveFileToLocalAndES(file);

            if (fileUrl == null) {
                return TextUtil.getJSONString(1, "上传文件失败");
            }
            return TextUtil.getJSONString(0, fileUrl);
        } catch (Exception e) {
            log.error("上传文件失败" + e.getMessage());
            return TextUtil.getJSONString(1, "上传失败");
        }
    }


    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("/get/text")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id" , defaultValue = "") String id,
                              @RequestParam(name = "type" , defaultValue = "") String type){

        if (id.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        GetResponse response = this.transportClient.prepareGet("text",type,id).get();

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(response.getSource(), HttpStatus.OK);

    }


    @PostMapping("/add/text")
    public ResponseEntity add(@RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "type") String type,
                              @RequestParam("file") MultipartFile file){
        try {
            String fileName = file.getOriginalFilename();
            Files.copy(file.getInputStream(), new File(TextUtil.FILE_DIR + fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            String string = TextUtil.readToString(TextUtil.FILE_DIR + fileName);
            Text text = new Text(string);
            XContentBuilder content =  XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title",title)
                    .field("author",author)
                    .field("type",type)
                    .field("update_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .field("TextContent",text)
                    .endObject();
            IndexResponse result = transportClient.prepareIndex("text",type)
                    .setSource(content)
                    .get();
            return new ResponseEntity(result.getId(),HttpStatus.OK);
        } catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
