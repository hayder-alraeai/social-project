package com.hayder.socialprojekt.controller;

import com.hayder.socialprojekt.SocialProjektApplication;
import com.hayder.socialprojekt.jwt.RequestAuthenticationModel;
import com.hayder.socialprojekt.model.Post;
import com.hayder.socialprojekt.service.PostService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = SocialProjektApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class PostControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private PostController postController;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Autowired
    private TestRestTemplate restTemplate;

    private String getToken() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/login";
        URI uri = new URI(baseUrl);
        RequestAuthenticationModel user = new RequestAuthenticationModel("admin", "admin");

        HttpEntity<RequestAuthenticationModel> request = new HttpEntity<>(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        //Verify request succeed (login)
        Assert.assertEquals(200, result.getStatusCodeValue());
        return "Bearer " + result.getBody().toString();
    }

    @Test
    public void getAllPostsTest() throws URISyntaxException{
        System.out.println(getToken());
        assertThat(postController.getAllPosts()).isNotNull();
    }
    @Test
    public void addPostTest() throws URISyntaxException{
        Post post = new Post("test test test");
        assertThat(postController.addPost(post, getToken())).isNotNull()
                .hasFieldOrPropertyWithValue("postContent", "test test test");
        System.out.println(postController.getAllPosts());
    }
    @Test
    public void deletePostTest() throws URISyntaxException{
        Post post = new Post("content");
        postController.addPost(post, getToken());
        postController.deletePost(1);
        assertThat(postController.getAllPosts()).size().isEqualTo(0);
    }
    @Test
    public void updatePostTest() throws URISyntaxException{
        Post post1 = new Post("content");
        Post post2 = new Post("content2");
        postController.addPost(post1, getToken());
        postController.updatePost(1,post2);
        assertThat(postController.getOnePost(1).getPostContent()).isEqualTo(post2.getPostContent());
    }
}