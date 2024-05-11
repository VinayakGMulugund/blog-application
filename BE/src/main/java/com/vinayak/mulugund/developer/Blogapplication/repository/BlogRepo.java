package com.vinayak.mulugund.developer.Blogapplication.repository;

import com.vinayak.mulugund.developer.Blogapplication.model.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepo extends MongoRepository<Blog, String> {
}
