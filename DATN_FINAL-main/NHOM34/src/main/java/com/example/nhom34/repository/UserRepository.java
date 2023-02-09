package com.example.nhom34.repository;

import com.example.nhom34.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByStatus(Integer status);
}
