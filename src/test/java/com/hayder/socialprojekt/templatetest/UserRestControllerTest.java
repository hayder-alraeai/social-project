package com.hayder.socialprojekt.templatetest;

import com.hayder.socialprojekt.SocialProjektApplication;
import com.hayder.socialprojekt.controller.UserController;
import com.hayder.socialprojekt.jwt.JwtUtils;
import com.hayder.socialprojekt.jwt.RequestAuthenticationModel;
import com.hayder.socialprojekt.model.ApplicationUser;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = SocialProjektApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class UserRestControllerTest {
    @LocalServerPort
    private int port;

    private final String URL_HOST = "http://localhost:8080/api/";

    @Autowired
    private UserController userController;

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


    private String getToken() throws URISyntaxException{
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
    public void getAllUsersTest() throws URISyntaxException {

        final String getAll = URL_HOST + "v1/users";
        URI uriGet = new URI(getAll);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uriGet, HttpMethod.GET, entity,String.class );
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }
    @Test
    public void getOneUserTest() throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/users/1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }

    @Test
    public void addUserTest() throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/users/register");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        ApplicationUser user = new ApplicationUser("test", "test", "test");
        HttpEntity<ApplicationUser> entity = new HttpEntity<>(user,httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        Assert.assertEquals(201, rs.getStatusCodeValue());
    }
    @Test
    public void updateUserTest() throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/users/1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        ApplicationUser user = new ApplicationUser("test", "test", "test");
        HttpEntity<ApplicationUser> entity = new HttpEntity<>(user,httpHeaders);
        ResponseEntity<String> rs = restTemplate.exchange(uri, HttpMethod.PUT, entity, String.class);
        Assert.assertEquals(200, rs.getStatusCodeValue());
    }
    @Test
    public void deleteUserTest()throws URISyntaxException{
        URI uri = new URI(URL_HOST + "v1/users/1");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity res = restTemplate.exchange(uri, HttpMethod.DELETE, entity, String.class );
        Assert.assertEquals(200, res.getStatusCodeValue());
    }

}
