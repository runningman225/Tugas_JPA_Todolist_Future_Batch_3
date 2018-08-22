package com.todolist.todolist.repository;

import com.todolist.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    List<User> findAllBy();
}
