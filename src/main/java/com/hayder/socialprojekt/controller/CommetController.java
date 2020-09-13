package com.hayder.socialprojekt.controller;

import com.hayder.socialprojekt.jwt.JwtUtils;
import com.hayder.socialprojekt.model.ApplicationUser;
import com.hayder.socialprojekt.model.Comment;
import com.hayder.socialprojekt.model.Post;
import com.hayder.socialprojekt.service.CommentService;
import com.hayder.socialprojekt.service.PostService;
import com.hayder.socialprojekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/comments")
public class CommetController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("{id}")
    List<Comment> getByPost(@PathVariable long id){
        try {
            Post post = postService.getOne(id);
            return commentService.getCommentByPost(post);
        }catch (NullPointerException e){
            return null;
        }

    }
    //in this method we extract the user from the header and the post from the url and update the amount comments in a specific post
    @PostMapping("{postId}")
    Comment addComment(@RequestBody Comment comment, @PathVariable  long postId, @RequestHeader(name = "Authorization") String auth){
        ApplicationUser user = userService.findUserByUsername(jwtUtils.extractUsername(auth.substring(7))); //getting user by token then username
        Post post = postService.getOne(postId); //getting the post from id
        comment.setUser(user);
        comment.setPost(post);
        commentService.addComment(comment);
        post.setComments(commentService.getCommentByPost(post).size()); //set the amount comments in a specific post
        System.out.println(post.getComments());
        postService.updatePost(post.getPostId(), post);
        return null;
    }
    @DeleteMapping("{postId}")
    void deleteComment(@RequestBody Comment comment, @RequestHeader("Authorization") String auth, @PathVariable long postId){
        ApplicationUser user = userService.findUserByUsername(jwtUtils.extractUsername(auth.substring(7)));
        Post post = postService.getOne(postId);
        comment.setUser(user);
        comment.setPost(post);
        commentService.deleteComment(comment);
        post.setComments(commentService.getCommentByPost(post).size()); //set the amount comments in a specific post. in this case -1
        postService.updatePost(postId, post);
    }

}
