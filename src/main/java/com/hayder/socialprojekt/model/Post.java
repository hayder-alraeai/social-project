package com.hayder.socialprojekt.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private String postContent;
    @ManyToOne
    private ApplicationUser user; //the username of the user who created this post
    private long comments;
    private Date postTimestamp;

    public Post() {
        this.postTimestamp = new Date();
    }

    public Post(String postContent) {
        this.postTimestamp = new Date();
        this.postContent = postContent;
        this.user = user;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public Date getPostTimestamp() {
        return postTimestamp;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return getPostId() == post.getPostId() &&
                Objects.equals(getPostContent(), post.getPostContent()) &&
                Objects.equals(getUser(), post.getUser()) &&
                Objects.equals(getPostTimestamp(), post.getPostTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getPostContent(), getUser(), getPostTimestamp());
    }
}
