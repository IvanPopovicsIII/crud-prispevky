package com.example.crudPrispevky.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	Optional<Post> findByUserId(Integer userId);
	
}
