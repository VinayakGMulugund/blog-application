package com.vinayak.mulugund.developer.Blogapplication.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
public class Blog {
    @Id
    private String id;
    private String body;
    private String author;
    private String title;
    private List<String> tags;
    private List<String> comments;
    private String authorId;
    private long date;

    public Blog(String id, String body, String author, String title, List<String> tags, List<String> comments, String authorId, long date) {
        this.id = id;
        this.body = body;
        this.author = author;
        this.title = title;
        this.tags = tags;
        this.comments = comments;
        this.authorId = authorId;
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
