package com.asish.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class XlsxProjectApplication {

	@Autowired
//	private Test test;

	public static void main(String[] args) {
		SpringApplication.run(XlsxProjectApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
//		test.start();
	}
}
