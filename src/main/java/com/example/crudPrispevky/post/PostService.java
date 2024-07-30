package com.example.crudPrispevky.post;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PostService {

	private final RestClient restclient;
	
	@Autowired
	private PostRepository postRepository;
	
	public PostService(RestClient.Builder builder, PostRepository repository) {
		this.restclient = builder
				.baseUrl("https://jsonplaceholder.typicode.com/")
				.build();
		this.postRepository = repository;
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
	
	public boolean doesPostExistInDb(Integer id) {
		Optional <Post> post = postRepository.findById(id);
		return post.isPresent();
	}
	
}
