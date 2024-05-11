package com.vinayak.mulugund.developer.Blogapplication.controller;

import com.vinayak.mulugund.developer.Blogapplication.model.Blog;
import com.vinayak.mulugund.developer.Blogapplication.service.BlogService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    public List<Blog> getBlogs() {
        return blogService.getRepo().findAll();
    }
    @GetMapping("/blog/{id}")
    public Optional<Blog> getBlog(@PathVariable String id) {
        return blogService.getRepo().findById(id);
    }

    @PostMapping("/blog")
    public Blog postBlog(@RequestBody Blog blog) {
        return blogService.postBlog(blog);
    }
    @PutMapping("/blog")
    public Blog putBlog(@RequestBody Blog blog, @RequestParam String id, HttpServletResponse responseServlet) {
        Blog resBlog = blogService.editBlog(blog, id);
        if (resBlog == null)
            responseServlet.setStatus(403);
        return resBlog;
    }
    @DeleteMapping("/blog")
    public void deleteBlog(@RequestParam String id) {
        blogService.getRepo().deleteById(id);
    }
}

