package com.example.crudPrispevky.user;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserService {
	
	private final RestClient restclient;
	
	public UserService(RestClient.Builder builder) {
		this.restclient = builder
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.build();
	}
	
	public boolean doesUserExist(Integer id) {
		return this.getUserById(id) == null ? false : true;
	}
	
	public User getUserById(Integer id) {
		return restclient.get()
			.uri("users/{id}", id)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
			      throw new UserNotFoundException( id.toString() );
			  })
			.body(User.class);
		
	}
	
}
