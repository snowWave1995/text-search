package com.snowwave.textsearch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
@EnableScheduling
@EnableAsync
public class TextSearchApplication {



	@RequestMapping(path={"/","/index"})
	public String index(){
		return "search";
	}

	@RequestMapping(path={"list"})
	public String list(){
		return "list";
	}
	@RequestMapping(path={"detail"})
	public String detail(){
		return "detail";
	}

	public static void main(String[] args) {
		//PO转DTO

		SpringApplication.run(TextSearchApplication.class, args);
	}
}
