package com.example.crudPrispevky.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestClientTest(PostService.class)
class PostServiceTest {


    @Autowired
    MockRestServiceServer server;

    @Autowired
    PostService service;

    @Autowired
    ObjectMapper objectMapper;
	
    @Test
    void shouldFindPostById() throws JsonProcessingException {
    	
        // given
        Post post = new Post(1,
                1,
                "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto");

        // when
        this.server.expect(requestTo("https://jsonplaceholder.typicode.com/posts/1"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(post), MediaType.APPLICATION_JSON));

        // then
        Post firstPost = service.getPostById(1);
        assertEquals(firstPost.getId(), 1);
        assertEquals(firstPost.getUserId(),1);
        assertEquals(firstPost.getTitle(), "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        assertEquals(firstPost.getBody(), "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto");
    
    }
}