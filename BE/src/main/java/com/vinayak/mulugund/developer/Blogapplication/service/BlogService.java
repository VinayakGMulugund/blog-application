package com.vinayak.mulugund.developer.Blogapplication.service;

import com.vinayak.mulugund.developer.Blogapplication.model.Blog;
import com.vinayak.mulugund.developer.Blogapplication.model.User;
import com.vinayak.mulugund.developer.Blogapplication.model.UserPrincipal;
import com.vinayak.mulugund.developer.Blogapplication.repository.BlogRepo;
import com.vinayak.mulugund.developer.Blogapplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserRepo userRepo;

    public BlogRepo getRepo() {
        return blogRepo;
    }

    public Blog postBlog(Blog blog) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByUsername(userPrincipal.getUsername());
        blog.setAuthorId(user.getId());
        return blogRepo.save(blog);
    }

    public Blog editBlog(Blog blog, String blogId) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByUsername(userPrincipal.getUsername());
        String userId = user.getId();

        Optional<Blog> currentBlog = blogRepo.findById(blogId);
        if (currentBlog.isPresent()) {
            if (currentBlog.get().getAuthorId() == null || !currentBlog.get().getAuthorId().equals(userId))
                return null;
            else
                return blogRepo.save(blog);
        }

        blog.setAuthorId(userId);
        return blogRepo.save(blog);
    }
}
