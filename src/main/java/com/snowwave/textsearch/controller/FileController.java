package com.snowwave.textsearch.controller;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangfuqiang on 2018/1/25.
 */
@Controller
public class FileController {

    @Autowired
    private TransportClient transportClient;


    @GetMapping("/get/book/novel")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id" , defaultValue = "") String id){

        if (id.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        GetResponse response = this.transportClient.prepareGet("book","novel",id).get();

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(response.getSource(), HttpStatus.OK);

    }


    @PostMapping("/add/book/novel")
    public ResponseEntity add(@RequestParam(name = "title") String title,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "word_count") int wordCount,
                              @RequestParam(name = "publish_date")
                              @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                      Date publishDate){
        try {
            XContentBuilder content =  XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title",title)
                    .field("author",author)
                    .field("word_count",wordCount)
                    .field("publish_date",publishDate.getTime())
                    .endObject();
            IndexResponse result = this.transportClient.prepareIndex("book","novel")
                    .setSource(content)
                    .get();
            return new ResponseEntity(result.getId(),HttpStatus.OK);
        } catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
