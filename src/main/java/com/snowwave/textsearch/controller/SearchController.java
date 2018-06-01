package com.snowwave.textsearch.controller;
import com.snowwave.textsearch.model.RetDTO;
import com.snowwave.textsearch.model.SearchDTO;
import com.snowwave.textsearch.service.FileService;
import com.snowwave.textsearch.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteResponse;
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

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhangfuqiang on 2018/1/25.
 */
@Controller
@Slf4j
public class SearchController {

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
                      @RequestParam(name = "type" , defaultValue = "") String type) throws Exception{
        if (id.isEmpty()){
            return new RetDTO(404,"id为空");
        }

        GetResponse response = this.transportClient.prepareGet("text",type,id).get();

        if (!response.isExists()) {
            return new RetDTO(404,"未查到");
        }
        return RetDTO.getReturnJson(response.getSource());

    }


    /**
     * 上传文件到ES
     * @param title
     * @param author
     * @param type
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/add/text")
    @ResponseBody
    public RetDTO add(@RequestParam(name = "title" ) String title, @RequestParam(name = "author") String author,
                      @RequestParam(name = "type") String type, @RequestParam("file") MultipartFile file) throws Exception {
        HashMap<String,String> res = new HashMap();
        String string = null;
        string = fileService.getStringFromFile(file);
        Text text = new Text(string);
        try {
            XContentBuilder content =  XContentFactory.jsonBuilder().startObject().field("title",title)
                    .field("author",author).field("type",type).field("update_time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .field("TextContent",text).endObject();
            IndexResponse result = transportClient.prepareIndex("text",type).setSource(content).get();
            res.put("result",result.getId());
        } catch (Exception e){
            return new RetDTO(500,"上传失败");
        }
        return RetDTO.getReturnJson(res);
    }

    /**
     * 全文搜索
     * @param keyword
     * @return
     */
    @GetMapping("/searchAll")
    @ResponseBody
    public RetDTO searchAll(@RequestParam(name = "keyword") String keyword) throws Exception{
        String key = URLDecoder.decode(URLDecoder.decode(keyword, "UTF-8"),"UTF-8");
        SearchDTO searchDTO = searchService.searchAll(key);
        if (searchDTO.getTotal() == 0){
            return new RetDTO(404,"没有找到该内容");
        }
        return RetDTO.getReturnJson(searchDTO);
    }

    /**
     * 删除
     * @param type
     * @param id
     * @return
     */
    @DeleteMapping("/delete/text")
    @ResponseBody
    public RetDTO delete(@RequestParam(name = "type") String type, @RequestParam(name = "id") String id) {
        DeleteResponse response =  transportClient.prepareDelete("text",type,id).get();
        Map<String, Object> result = new HashMap<>();
        result.put("result",response.getResult().toString());
        return RetDTO.getReturnJson(result);
    }
}
