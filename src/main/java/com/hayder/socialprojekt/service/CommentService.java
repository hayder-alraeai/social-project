package com.hayder.socialprojekt.service;

import com.hayder.socialprojekt.dao.CommentRepository;
import com.hayder.socialprojekt.model.ApplicationUser;
import com.hayder.socialprojekt.model.Comment;
import com.hayder.socialprojekt.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentByPost(Post post){
        return commentRepository.findAll().stream().filter(c -> c.getPost().getPostId() == post.getPostId())
                .collect(Collectors.toList());
    }
    public Comment addComment(Comment comment){
        return commentRepository.save(comment);
    }
    public void deleteComment(Comment comment){
         commentRepository.delete(comment);
    }
}
