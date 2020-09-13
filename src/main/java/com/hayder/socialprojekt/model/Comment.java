package com.hayder.socialprojekt.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private ApplicationUser user;
    @ManyToOne
    private Post post;
    private String content;
    private Date commentTimeStamp;

    public Comment(String content) {
        this.commentTimeStamp = new Date();
        this.content = content;
        this.post.setComments(1);
    }

    public Comment() {
        this.commentTimeStamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTimeStamp() {
        return commentTimeStamp;
    }

    public void setCommentTimeStamp(Date commentTimeStamp) {
        this.commentTimeStamp = commentTimeStamp;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getId().equals(comment.getId()) &&
                getUser().equals(comment.getUser()) &&
                getPost().equals(comment.getPost()) &&
                getContent().equals(comment.getContent()) &&
                getCommentTimeStamp().equals(comment.getCommentTimeStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPost(), getContent(), getCommentTimeStamp());
    }
}
