package com.snowwave.textsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Slf4j
@SpringBootApplication
@RestController
public class TextSearchApplication {

	@Autowired
	private TransportClient transportClient;


	@GetMapping("/index")
	public String index(){
		return "index";
	}

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

	public static void main(String[] args) {
		SpringApplication.run(TextSearchApplication.class, args);
	}
}
