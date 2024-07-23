package com.example.crudPrispevky.post;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostJpaResourceTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.0");

	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	PostRepository repository;
	
	@BeforeEach
	void setUp() {
		List<Post> posts = List.of(	
					new Post( 1, 3, "title2", "body2"),
					new Post( 2, 5, "title3", "body3"),
					new Post( 3, 55, "title4", "body4"),
					new Post( 4, 56, "title5", "body5")
					);
		
		repository.saveAll(posts);
	}
	
	
	
	@Test
	void shouldFindAllPosts() {
		Post[] posts = restTemplate.getForObject("/api/posts/testing", Post[].class);
		assertThat(posts.length).isEqualTo(4);
	}
	
	@Test
	void shouldFindPostByValidId() {
		ResponseEntity<Post> response = restTemplate.exchange("/api/posts/2", HttpMethod.GET, null, Post.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();

	}
	
	@Test 
	void shouldThrowNotFoundWhenInvalidPostId() {
		ResponseEntity<Post> response = restTemplate.exchange("/api/posts/99", HttpMethod.GET, null, Post.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	
	@Test
	//@Rollback
	void shouldCreateNewPostWithValidId() {
		Post post = new Post( 5, 3, "title created in test", "body created in test");
		ResponseEntity<Post> response = restTemplate.exchange("/api/posts", HttpMethod.POST, new HttpEntity<Post>(post), Post.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(5);
        assertThat(response.getBody().getUserId()).isEqualTo(3);
        assertThat(response.getBody().getTitle()).isEqualTo("title created in test");
        assertThat(response.getBody().getBody()).isEqualTo("body created in test");
	}
	
	@Test
	void shouldNotCreateNewPostWithInvalidData() {
        Post post = new Post(101,1,"","");
        ResponseEntity<Post> response = restTemplate.exchange("/api/posts", HttpMethod.POST, new HttpEntity<Post>(post), Post.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

	 @Test
     void shouldUpdatePostWhenPostIsValid() {
	    ResponseEntity<Post> response = restTemplate.exchange("/api/posts/3", HttpMethod.GET, null, Post.class);
	    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	
	    Post existing = response.getBody();
	    assertThat(existing).isNotNull();
	    Post updated = new Post(existing.getId(),existing.getUserId(),"Title 3 was updated", "Body 3 was updated");
		
	    ResponseEntity<Post> responseAfterUpdate = restTemplate.exchange("/api/posts/{post_id}", HttpMethod.PUT, new HttpEntity<Post>(updated), Post.class, existing.getId());
		assertThat(responseAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseAfterUpdate.getBody()).isNotNull();
        assertThat(responseAfterUpdate.getBody().getTitle()).isEqualTo("Title 3 was updated");
        assertThat(responseAfterUpdate.getBody().getBody()).isEqualTo("Body 3 was updated");
	  
//	    
//	    assertThat(updated.getId()).isEqualTo(3);
//	    assertThat(updated.getUserId()).isEqualTo(55);
//	    assertThat(updated.getTitle()).isEqualTo("Title 3 was updated");
//	    assertThat(updated.getBody()).isEqualTo("Body 3 was updated");
    }
	
	 @Test
     void shouldDeleteWithValidID() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/posts/4", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
     }

}