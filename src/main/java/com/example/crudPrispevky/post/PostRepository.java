package com.example.crudPrispevky.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
//	List<Post> findAll();
//	
//	Optional<Post> findById(Integer id);
//
//	void create(Post run);
//
//	void update(Post run, Integer id);
//
//	void delete(Integer id);
//	    
}
