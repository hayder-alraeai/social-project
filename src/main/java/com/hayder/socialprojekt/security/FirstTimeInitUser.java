package com.hayder.socialprojekt.security;

import com.hayder.socialprojekt.model.ApplicationUser;
import com.hayder.socialprojekt.model.Post;
import com.hayder.socialprojekt.service.PostService;
import com.hayder.socialprojekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeInitUser implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;


    @Override
    public void run(String... args) throws Exception {
        //CHECK IF WE HAVE USER IN DATABASE
        //IF NO USER EXIST ADD A USER

        if (userService.getAllUsers().isEmpty()){
            ApplicationUser user = new ApplicationUser("admin", "admin@admin", "admin");
            userService.addUser(user);
            System.out.println("An user has been added, username: 'admin' password 'admin' ");
            if (postService.getAll().isEmpty()){
                Post post = new Post("this is a test");
                post.setUser(user);
                postService.addPost(post);
                System.out.println("post has been added by " + post.getUser().getName());
            }
        }
    }
}
