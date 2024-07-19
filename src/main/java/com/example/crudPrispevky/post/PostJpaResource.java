package com.example.crudPrispevky.post;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostJpaResource {

	private PostRepository postRepository;

	public PostJpaResource(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}
	
	@GetMapping("/testing")
	public List<Post> retrieveAllPosts() {
		return postRepository.findAll();
		
	}

	@GetMapping("/{id}")
	public Optional<Post> retrievePostDetailsById(@PathVariable int postId) {
		
		Optional<Post> post = postRepository.findById(postId);

		if (post.isEmpty()) {
			
			 // dohladanie cez externu api
		}
		
		return post;
	}
	
	@GetMapping("/{user_id}")
	public Optional<Post> retrievePostDetailsByUserId(@PathVariable int user_id) {
		
		Optional<Post> post = postRepository.findById(user_id);

		if (post.isEmpty()) {
			throw new PostNotFoundException("user_id:" + user_id);
		}
		
		return post;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public ResponseEntity<Object> createPost( @Valid @RequestBody Post post) {

		// validovat usera cez api
		
		Post savedPost = postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public ResponseEntity<Object> updatePost( @Valid @RequestBody Post post, @PathVariable int id) {
		Optional<Post> foundPost = postRepository.findById(id);

		if (foundPost.isEmpty()) {
			throw new PostNotFoundException("id:" + id);
		}
		
		Post myPost = foundPost.get();
	
		myPost.setBody(post.getBody());
		myPost.setTitle(post.getTitle());
		
		postRepository.save(myPost);
		return ResponseEntity.noContent().build();
		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePost(@Valid @PathVariable int id) {
		Optional<Post> foundPost = postRepository.findById(id);

		if (foundPost.isEmpty()) {
			throw new PostNotFoundException("id:" + id);
		}
		
		postRepository.delete(foundPost.orElseThrow());
		
		return ResponseEntity.noContent().build();
		
	}
}
