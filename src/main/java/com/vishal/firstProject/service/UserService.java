package com.vishal.firstProject.service;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.repository.UserRepository;
import lombok.Data;

@Component
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // create user
    public void createUser(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Arrays.asList("user"));

        userRepository.save(newUser);
    }

    // get all user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // get one user by id
    public User getUser(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    // delete user by id
    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }

    // get user by username
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
