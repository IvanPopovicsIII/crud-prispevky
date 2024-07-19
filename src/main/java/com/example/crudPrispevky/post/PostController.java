package com.example.crudPrispevky.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


public class PostController {

	@GetMapping("/hello")
	 String home( ) {
		return "what up homie";
	}
	
}
