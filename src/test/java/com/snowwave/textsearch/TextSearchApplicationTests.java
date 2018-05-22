package com.snowwave.textsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TextSearchApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("zfq1995@126.com");
		message.setTo("2065095202@qq.com");
		message.setSubject("来自父亲的问候");
		message.setText("儿子你好吗");

		for (int i = 0; i < 100; i++) {
			mailSender.send(message);
		}

	}

}
