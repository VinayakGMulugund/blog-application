package com.vinayak.mulugund.developer.Blogapplication.controller;

import com.vinayak.mulugund.developer.Blogapplication.model.User;
import com.vinayak.mulugund.developer.Blogapplication.service.JwtService;
import com.vinayak.mulugund.developer.Blogapplication.service.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Map<String, Object> map = new HashMap<>();

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(username);
            map.put("userId", userDetailService.getAuthoredUser().getId());
            map.put("token", token);
            return ResponseEntity.ok(map);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody User user, HttpServletResponse httpServletResponse) {
        User registeredUser = userDetailService.registerUser(user);
        if (registeredUser == null) {
            httpServletResponse.setStatus(403);
        }
        return registeredUser;
    }


    @PutMapping("/user")
    public User putMapping(@RequestBody User user) {
        return userDetailService.updateUser(user);
    }

    @DeleteMapping("/user")
    public User delete() {
        String username = userDetailService.getAuthoredUser().getUsername();
        System.out.println(username);
        return userDetailService.deleteByUsername(username);
    }

    @GetMapping("/user/logout")
    public void logout() {
        jwtService.revokeToken();
    }
}
