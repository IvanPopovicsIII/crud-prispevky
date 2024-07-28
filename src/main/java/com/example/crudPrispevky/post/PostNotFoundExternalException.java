package com.example.crudPrispevky.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundExternalException extends RuntimeException{

	public PostNotFoundExternalException(String message) {
		super("post with id : " + message + " does not exist in either inner postgres or external api");
	}
	
}
