package com.hayder.socialprojekt.templatetest;

import com.hayder.socialprojekt.SocialProjektApplication;
import com.hayder.socialprojekt.jwt.JwtUtils;
import com.hayder.socialprojekt.jwt.RequestAuthenticationModel;
import com.hayder.socialprojekt.model.Post;
import com.hayder.socialprojekt.security.FirstTimeInitUser;
import com.hayder.socialprojekt.service.PostService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = SocialProjektApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class PostRestControllerTest {
    @LocalServerPort
    private int port;

    private final String URL_HOST = "http://localhost:8080/api/";

    @Autowired
    private PostService postService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    private String getToken() throws URISyntaxException {

        final String baseUrl = URL_HOST + "login";
        URI uri = new URI(baseUrl);
        RequestAuthenticationModel user = new RequestAuthenticationModel("admin", "admin");

        HttpEntity<RequestAuthenticationModel> request = new HttpEntity<>(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        //Verify request succeed (login)
        Assert.assertEquals(200, result.getStatusCodeValue());
        return result.getBody().toString();
    }
    @Test
    public void getAllPostsTest() throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/posts");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }
    @Test
    public void getOnePostTest()throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/posts/1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }
    @Test
    public void updatePostTest()throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/posts/1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        Post post = new Post("test");
        HttpEntity<Post> entity = new HttpEntity<>(post,httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }
    @Test
    public void addPostTest()throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/posts");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        Post post = new Post("test");
        HttpEntity<Post> entity = new HttpEntity<>(post,httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }
    @Test
    public void deletePostTest()throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/posts/1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity res = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class );
        Assert.assertEquals(200, res.getStatusCodeValue());
    }
}
