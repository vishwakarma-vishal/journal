package com.vishal.firstProject.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // create new user
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {

        userService.createUser(user);

        return new ResponseEntity<>("User is created sucessfully!", HttpStatus.CREATED);
    }

    // get all user
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> all = userService.getAllUsers();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    // get a user by id
    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId myId) {

        User user = userService.getUserById(myId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // delete user by id
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteUserById(@PathVariable ObjectId myId) {

        User userInDb = userService.getUserById(myId);

        if (userInDb == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            userService.deleteUserById(myId);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }
    }

    // update user by id
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUserByid(@RequestBody User newUser, @PathVariable String userName) {
        User oldUser = userService.findByUserName(userName);

        if (oldUser != null) {
            oldUser.setUserName(newUser.getUserName());
            oldUser.setPassword(newUser.getPassword());

            userService.createUser(oldUser);

            return new ResponseEntity<>("User is updated", HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

}
