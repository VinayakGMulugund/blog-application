package com.vinayak.mulugund.developer.Blogapplication.repository;

import com.vinayak.mulugund.developer.Blogapplication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepo extends MongoRepository<User, String> {
    User findByUsername(String username);
    User deleteByUsername(String username);
}
