package com.snowwave.textsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class TextSearchApplication {

	@RequestMapping(path={"/","/index"})
	public String index(){
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(TextSearchApplication.class, args);
	}
}
