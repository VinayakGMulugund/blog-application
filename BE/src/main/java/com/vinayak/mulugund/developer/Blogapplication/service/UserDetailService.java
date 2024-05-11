package com.vinayak.mulugund.developer.Blogapplication.service;

import com.vinayak.mulugund.developer.Blogapplication.model.User;
import com.vinayak.mulugund.developer.Blogapplication.model.UserPrincipal;
import com.vinayak.mulugund.developer.Blogapplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    private User authoredUser;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("user is not found inside the Users collection");
        }
        this.authoredUser = user;
        return new UserPrincipal(user);
    }


    public User registerUser(User user) {
        User existingUser = repo.findByUsername(user.getUsername());
        if (existingUser == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            return repo.save(user);
        }
        return null;
    }

    public User updateUser(User user) {
        User existingUser = repo.findByUsername(user.getUsername());
        if (existingUser != null) {
            user.setId(existingUser.getId());
        }
        return repo.save(user);
    }


    public User deleteByUsername(String username) {
        return repo.deleteByUsername(username);
    }

    public User getAuthoredUser() {
        return this.authoredUser;
    }
}
