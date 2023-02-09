package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Integer id) {
		return userRepository.findById(id).get();
	}

	public List<User> getAdministrators() {
		return userRepository.getAdministrators();
	}

	@Override
	public User findByUsernameEquals(String username) {
		return userRepository.findByUsernameEquals(username);
	}

	@Override
	public User findByEmailEquals(String email) {
		return userRepository.findByEmailEquals(email);
	}

	@Override
	public User findByPhoneNumberEquals(String phoneNumber) {
		return userRepository.findByPhoneNumberEquals(phoneNumber);
	}

	@Override
	public User getCustomerById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User updateCustomerById(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public User saveCustomer(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public User findByUsernameAndStatusEquals(String username, Integer status) {
		return userRepository.findByUsernameAndStatusEquals(username, status);
	}

}