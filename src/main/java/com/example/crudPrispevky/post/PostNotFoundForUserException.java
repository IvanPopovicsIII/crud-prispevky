package com.example.crudPrispevky.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFoundForUserException extends RuntimeException{

	public PostNotFoundForUserException(String message) {
		super( "user with id : " + message + " has no posts");
	}
	
	
}
