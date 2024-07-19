package com.example.crudPrispevky.post;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer user_id;
	
	@Size(max=50)
	private String title;
	
	@Size(max=250)
	private String body;
	
	public Post(int id, int user_id, String title, String body) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", userId=" + user_id + ", title=" + title + ", body=" + body + "]";
	}

	public Post() {}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	
}
