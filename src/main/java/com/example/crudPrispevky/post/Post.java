package com.example.crudPrispevky.post;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@GenericGenerator( 	name = "UseExistingIdOtherwiseGenerateUsingIdentity",
					type = com.example.crudPrispevky.config.UseExistingIdOtherwiseGenerateUsingIdentity.class)
@Entity(name = "postEntity")
@Table(name = "posts")
public class Post {


	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
	private Integer id;

	private Integer userId;
	
	@NotEmpty
	@Size(max=100)
	private String title;
	
	@NotEmpty
	@Size(max=250)
	private String body;
	
	public Post(Integer id,Integer user_id, String title, String body) {
		this.id = id;
		this.userId = user_id;
		this.title = title;
		this.body = body;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", userId=" + userId + ", title=" + title + ", body=" + body + "]";
	}

	public Post() {}

	public int getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(Integer user_id) {
		this.userId = user_id;
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
