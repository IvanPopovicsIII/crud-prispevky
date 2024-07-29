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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Post", description = "Post management APIs")
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
	
	@Operation(
		      summary = "Retrieve all Posts from inner database",
		      description = "Get list of all Post objects stored in a database. ",
		      tags = { "get" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
	@GetMapping("")
	public List<Post> retrieveAllPosts() {
		
		return postRepository.findAll();
		
	}

	@Operation(
		      summary = "Retrieve a Post by Id",
		      description = "Get a Post object by specifying its id. If the post is not found, it is looked up on external API. Response is Post object with id, user_id, title and body",
		      tags = { "get" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{post_id}")
	public Post retrievePostDetailsById(@PathVariable Integer post_id) {
		
		Optional<Post> post = postRepository.findById(post_id);

		if (post.isEmpty()) {	
			Post externalPost = postService.getPostById(post_id);
			return externalPost;
		}
		
		return post.get();
	}
	
	@Operation(
		      summary = "Retrieve a Post by user_id",
		      description = "Get a Post object by specifying its user_id. The response is a list of Post objects with id, user_id, title and body",
		      tags = { "get" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/users/{user_id}")
	public List<Post> retrievePostsByUserId(@PathVariable Integer user_id) {
		
		//find by user id
		List<Post> posts = postRepository.findByUserId(user_id);
	
		if (posts.isEmpty()) {
			
			throw new PostNotFoundForUserException(user_id.toString());
		}
		
		return posts;
	}
	
	@Operation(
		      summary = "Create a Post object",
		      description = "Creates a Post object with specified parameters. Endpoint validates user_id via external API (https://jsonplaceholder.typicode.com/users). "
		      		+ "Response is Post object with id, user_id, title and body",
		      tags = { "post" })
	@ApiResponses({
	      @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public Post createPost( @Valid @RequestBody Post post) {
		
		if(!userService.doesUserExist(post.getUserId())) {
			// should not matter, as if when doesUserExist does not find user, it throws UserNotFound404 exception
			return null;
			};	
			
			
		return postRepository.save(post);
		
	}
	
	
	@Operation(
		      summary = "Update a Post object",
		      description = "Updates a Post object with specified parameters. Changable atribues of Post are title and body "
		      		+ "Response is Post object with id, user_id, title and body)",
		      tags = { "put" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{id}")
	public Post updatePost( @Valid @RequestBody Post post, @PathVariable Integer id) {
		Optional<Post> foundPost = postRepository.findById(id);

		if (foundPost.isEmpty()) {
			throw new PostNotFoundException(id.toString());
		}
		
		Post myPost = new Post( foundPost.get().getId(),
								foundPost.get().getUserId(),
								post.getTitle(),
								post.getBody());
	
		return postRepository.save(myPost);
		
	}
	@Operation(
		      summary = "Delets a Post object using id",
		      description = "Deletes a Post object specified by id. Returns empty response",
		      tags = { "delete" })
	@ApiResponses({
	      @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })})
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
