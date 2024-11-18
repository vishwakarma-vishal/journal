package com.vishal.firstProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public String Health() {
        return "Sab badhiya chal rha hai";
    }

    // create new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        
        User userInDb = userService.findByUserName(user.getUserName());
        if (userInDb != null) {
            return new ResponseEntity<>("User already exist!", HttpStatus.OK);
        }

        userService.createUser(user);

        return new ResponseEntity<>("User is created sucessfully!", HttpStatus.CREATED);
    }

}
