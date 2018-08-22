package com.todolist.todolist.controller;

import com.todolist.todolist.exception.ResourceNotFoundException;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User createUsers(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/users/{userid}")
    public User updateUsers(@PathVariable Long userid, @Valid @RequestBody User userRequest) {
        return userRepository.findById(userid).map(user -> {
            user.setName(userRequest.getName());
            user.setDescription(userRequest.getDescription());
            return userRepository.save(user);
        }).orElseThrow(()-> new
                ResourceNotFoundException("User not found with id " + userid));
    }

    @DeleteMapping("/users/{userid}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long userid) {
        return userRepository.findById(userid)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("user not found with id " + userid));
    }
}
