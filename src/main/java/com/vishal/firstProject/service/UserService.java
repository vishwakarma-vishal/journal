package com.vishal.firstProject.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vishal.firstProject.entity.User;
import com.vishal.firstProject.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // create user
    public void createUser(User newUser) {
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
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
