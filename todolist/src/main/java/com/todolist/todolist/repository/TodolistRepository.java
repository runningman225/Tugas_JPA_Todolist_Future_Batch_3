package com.todolist.todolist.repository;

import com.todolist.todolist.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface TodolistRepository  extends JpaRepository<TodoList, Long>{
    List<TodoList> findByUserId(Long userId);
}
