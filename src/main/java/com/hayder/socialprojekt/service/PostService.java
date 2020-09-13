package com.hayder.socialprojekt.service;

import com.hayder.socialprojekt.dao.PostRepository;
import com.hayder.socialprojekt.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAll(){
        return postRepository.findAll();
    }
    public Post getOne(long id){
        return postRepository.findById(id).get();
    }
    public Post addPost(Post post){
       return postRepository.save(post);
    }
    public void deletePost(long id){
        postRepository.deleteById(id);
    }
    public Post updatePost(long id, Post newPost){
        return postRepository.findById(id)
                .map(p -> {
                    p.setPostContent(newPost.getPostContent());
                    p.setComments(newPost.getComments());
                    return postRepository.save(p);
                })
                .orElseGet(()->{
                    newPost.setPostId(id);
                    return postRepository.save(newPost);
                });
    }

}
