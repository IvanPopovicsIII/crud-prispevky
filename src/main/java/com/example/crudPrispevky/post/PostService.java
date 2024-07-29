package com.example.crudPrispevky.post;


import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PostService {

	private final RestClient restclient;
		
	public PostService(RestClient.Builder builder) {
		this.restclient = builder
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.build();
	}
		
	public Post getPostById(Integer id) {
		return restclient.get()
						.uri("posts/{id}", id)
						.retrieve()
						.onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
						      throw new PostNotFoundExternalException( id.toString() );
						  })
						.body(Post.class);
	}
	
}
