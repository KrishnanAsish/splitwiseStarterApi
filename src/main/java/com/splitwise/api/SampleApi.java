package com.splitwise.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleApi {

	@RequestMapping("/hello")
	public String helloWorld() {
		return "I am working!!...";
	}
	
}
