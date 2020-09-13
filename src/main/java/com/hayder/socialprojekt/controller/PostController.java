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

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAll();
    }

    @GetMapping("{id}")
    public Post getOnePost(@PathVariable long id){
        return postService.getOne(id);
    }

    @PostMapping
    public Post addPost(@RequestBody Post post, @RequestHeader(name = "Authorization") String headerAuth){
        String token = headerAuth.substring(7);
        String username = jwtUtils.extractUsername(token);
        ApplicationUser user = userService.findUserByUsername(username);
        post.setUser(user);
        return postService.addPost(post);
    }
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable long id){
        //DELETING ALL RELATED COMMENTS FROM THE POST
        List<Comment> comments = commentService.getCommentByPost(postService.getOne(id));
        for (Comment c: comments){
            commentService.deleteComment(c);
        }
        //NOW DELETE THE POST
        postService.deletePost(id);
    }
    @PutMapping("{id}")
    public Post updatePost(@PathVariable long id, @RequestBody Post post){
        return postService.updatePost(id, post);
    }

}
