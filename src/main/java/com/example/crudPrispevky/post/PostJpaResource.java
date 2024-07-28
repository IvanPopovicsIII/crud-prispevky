package com.example.crudPrispevky.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.crudPrispevky.user.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostJpaResource {

	
	private PostRepository postRepository;

	private PostService postService;
	
	@Autowired		
	private UserService userService;
	
	public PostJpaResource(PostRepository postRepository, PostService postService) {
		super();
		this.postRepository = postRepository;
		this.postService = postService;
	}
	
	@GetMapping("/testing")
	public List<Post> retrieveAllPosts() {
		
		return postRepository.findAll();
		
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{post_id}")
	public Post retrievePostDetailsById(@PathVariable Integer post_id) {
		
		Optional<Post> post = postRepository.findById(post_id);

		if (post.isEmpty()) {	
			Post externalPost = postService.getPostById(post_id);
			return externalPost;
			 // dohladanie cez externu api
			 // ak nic, tak notFoundException
		}
		
		return post.get();
	}
	
	@GetMapping("/users/{user_id}")
	public List<Post> retrievePostsByUserId(@PathVariable Integer user_id) {
		
		//find by user id
		List<Post> posts = postRepository.findByUserId(user_id);

		//post.get().
		
		if (posts.isEmpty()) {
			throw new PostNotFoundForUserException(user_id.toString());
		}
		
		return posts;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public Post createPost( @Valid @RequestBody Post post) {
		
		if(!userService.doesUserExist(post.getUserId())) {
			// should not matter, as if when doesUserExist does not find user, it throws 404 exception
				return null;
			};	
			
			
		return postRepository.save(post);
		
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public Post updatePost( @Valid @RequestBody Post post, @PathVariable Integer id) {
		Optional<Post> foundPost = postRepository.findById(id);

		if (foundPost.isEmpty()) {
			throw new PostNotFoundException("id:" + id);
		}
		
		Post myPost = new Post( foundPost.get().getId(),
								foundPost.get().getUserId(),
								post.getTitle(),
								post.getBody());
	
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
