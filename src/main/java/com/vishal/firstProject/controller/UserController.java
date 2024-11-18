package com.vishal.firstProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.repository.UserRepository;
import com.vishal.firstProject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // update user by username
    @PutMapping
    public ResponseEntity<?> updateUserByid(@RequestBody User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUser = userService.findByUserName(userName);

        oldUser.setUserName(newUser.getUserName());
        oldUser.setPassword(newUser.getPassword());
        userService.createUser(oldUser);

        return new ResponseEntity<>("User is updated", HttpStatus.OK);
    }

    // delete user by username
    @DeleteMapping
    public ResponseEntity<String> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
