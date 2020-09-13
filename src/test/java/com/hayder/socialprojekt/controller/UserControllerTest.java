package com.hayder.socialprojekt.controller;

import com.hayder.socialprojekt.SocialProjektApplication;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = SocialProjektApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    private String getToken() throws URISyntaxException {
        final String baseUrl = "http://localhost:8080/api/login";
        URI uri = new URI(baseUrl);
        RequestAuthenticationModel user = new RequestAuthenticationModel("admin", "admin");

        HttpEntity<RequestAuthenticationModel> request = new HttpEntity<>(user);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        //Verify request succeed (login)
        Assert.assertEquals(200, result.getStatusCodeValue());
        return result.getBody().toString();
    }
    @Test
    public void getAllUsersTest(){
        assertThat(userController.getAllUsers()).isNotNull();
    }
    @Test
    public void getOneUserTest(){
        ApplicationUser user = new ApplicationUser("hayder", "email@email", "pass");
        userController.addNewUser(user);
        assertThat(userController.getOneUser(2).getName()).isEqualTo(user.getName());
    }
    @Test
    public void addUserTest(){
        ApplicationUser user = new ApplicationUser("hayder", "email@email", "pass");
        userController.addNewUser(user);
        assertThat(userController.getOneUser(2).getName()).isEqualTo(user.getName());
        assertThat(userController.getAllUsers()).size().isEqualTo(2);



    }
    @Test
    public void deleteUserTest(){
        ApplicationUser user = new ApplicationUser("hayder", "email@email", "pass");
        userController.addNewUser(user);
        userController.deleteUser(2);
        assertThat(userController.getAllUsers().size()).isEqualTo(1);
    }
    @Test
    public void updateUserTest(){
        ApplicationUser user = new ApplicationUser("hayder", "email@email", "pass");
        ApplicationUser user2 = new ApplicationUser("micke", "email@email", "pass");
        userController.addNewUser(user);
        userController.updateUser(2, user2);
        assertThat(userController.getOneUser(2).getName()).isEqualTo(user2.getName());
    }
}