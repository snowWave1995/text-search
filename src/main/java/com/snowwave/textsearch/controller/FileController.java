package com.snowwave.textsearch.controller;
import com.snowwave.textsearch.model.RetDTO;
import com.snowwave.textsearch.service.FileService;
import com.snowwave.textsearch.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private SearchService searchService;

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("/get/text")
    @ResponseBody
    public RetDTO get(@RequestParam(name = "id" , defaultValue = "") String id,
                      @RequestParam(name = "type" , defaultValue = "") String type){
        RetDTO retDTO = null;
        if (id.isEmpty()){
            return new RetDTO(404,"id为空");
        }

        GetResponse response = this.transportClient.prepareGet("text",type,id).get();

        if (!response.isExists()) {
            return new RetDTO(404,"未查到");
        }
        retDTO = RetDTO.getReturnJson(response.getSource());
        return retDTO;

    }


    @PostMapping("/add/text")
    public RetDTO add(@RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "type") String type,
                              @RequestParam("file") MultipartFile file) throws Exception{
        String string = fileService.getStringFromFile(file);
        Text text = new Text(string);
        try {

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
            return new RetDTO(200,result.getId());
        } catch (IOException e){
            e.printStackTrace();
            return new RetDTO(500,"增加文档失败");
        }

    }


    @GetMapping("/searchAll")
    public RetDTO searchAll(@RequestParam(name = "keyword") String keyword) {
        return RetDTO.getReturnJson(searchService.searchAll(keyword));
    }

}
