package com.todolist.todolist.controller;


import com.todolist.todolist.exception.ResourceNotFoundException;
import com.todolist.todolist.model.TodoList;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.TodolistRepository;
import com.todolist.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class TodolistController
{
    @Autowired
    private TodolistRepository todolistRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/todolists")
    public List<TodoList> getTodolistbyUserId(@PathVariable Long userId) {
        return todolistRepository.findByUserId(userId);

    }

    @PostMapping("/users/{userId}/todolists")
    public TodoList addTodoList(@PathVariable Long userId,
                                     @Valid @RequestBody TodoList todolist) {
        return userRepository.findById(userId)
                .map(user -> {
                    todolist.setUser(user);
                    return todolistRepository.save(todolist);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/users/{userId}/todolists/{todolistId}")
    public TodoList updateTodolist(@PathVariable Long userId,
                                   @PathVariable Long todolistId,
                                   @Valid @RequestBody TodoList todolistRequest) {
        if(!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Todolist not found with id " + userId);
        }
        return todolistRepository.findById(todolistId)
                .map(todolist -> {
                    todolist.setTitle(todolistRequest.getTitle());
                    todolist.setDescription(todolistRequest.getDescription());
                    return todolistRepository.save(todolist);
                }).orElseThrow(()->new ResourceNotFoundException("Todolist not found with id " +  todolistId));
    }

    @DeleteMapping("/users/{userId}/todolists/{todolistId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long userId,
                                          @PathVariable Long todolistId) {
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("Todolist not found with id "+ userId);
        }

        return todolistRepository.findById(todolistId)
                .map(todolist -> {
                    todolistRepository.delete(todolist);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("Todolist not found with id " + todolistId));

    }



}
