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

	//@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{post_id}")
	public Optional<Post> retrievePostDetailsById(@PathVariable Integer post_id) {
		
		Optional<Post> post = postRepository.findById(post_id);

		if (post.isEmpty()) {
			
			 // dohladanie cez externu api
			throw new PostNotFoundException("post_id:" + post_id);
		}
		
		return post;
	}
	
	@GetMapping("/users/{user_id}")
	public Optional<Post> retrievePostDetailsByUserId(@PathVariable Integer user_id) {
		
		//find by user id
		Optional<Post> post = postRepository.findByUserId(user_id);

		if (post.isEmpty()) {
			throw new PostNotFoundException("user_id:" + user_id);
		}
		
		return post;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public Post createPost( @Valid @RequestBody Post post) {

		// validovat usera cez api
		
		Post savedPost = postRepository.save(post);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
//				.toUri();
		return savedPost;
		//return ResponseEntity.created(location).build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public Post updatePost( @Valid @RequestBody Post post, @PathVariable Integer id) {
		Optional<Post> foundPost = postRepository.findById(id);

		if (foundPost.isEmpty()) {
			throw new PostNotFoundException("id:" + id);
		}
		
		Post myPost = foundPost.get();
	
		myPost.setBody(post.getBody());
		myPost.setTitle(post.getTitle());
		
		return postRepository.save(myPost);
		
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePost(@Valid @PathVariable Integer id) {
		Optional<Post> foundPost = postRepository.findById(id);

		if (foundPost.isEmpty()) {
			throw new PostNotFoundException("id:" + id);
		}
		
		postRepository.delete(foundPost.orElseThrow());
		
		return ResponseEntity.noContent().build();
		
	}
}
