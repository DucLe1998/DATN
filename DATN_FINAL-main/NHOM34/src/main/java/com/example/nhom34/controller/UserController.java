package com.example.nhom34.controller;

import com.example.nhom34.entities.Product;
import com.example.nhom34.entities.User;
import com.example.nhom34.repository.ProductRepository;
import com.example.nhom34.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    UserRepository userRepository;

    @GetMapping("/{status}")
    public List<User> findAll(
            @PathVariable("status") Integer status
    ){
        return this.userRepository.findUsersByStatus(status);
    }
}
