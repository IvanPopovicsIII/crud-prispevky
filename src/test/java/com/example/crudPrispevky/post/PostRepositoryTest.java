package com.example.crudPrispevky.post;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.0");

	@Autowired
	PostRepository repository;



	@BeforeEach
	void setUp() {
		List<Post> posts = List.of(
					new Post(
							1,
							3,
							"title1",
							"body1"), 
					new Post(
							2,
							3,
							"title2",
							"body2"),
					new Post(
							3,
							5,
							"title3",
							"body3"));
		
		repository.saveAll(posts);
	}
		

		
	@Test
	void connectionEstablished() {
		assertThat(postgres.isCreated()).isTrue();
		assertThat(postgres.isRunning()).isTrue();
	}

	@Test
	void shouldReturnByUserId() {
		Optional<Post> post = repository.findByUserId(5);
		assertThat(post).isNotNull();
		
	}
		
}
