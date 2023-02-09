package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.User;

public interface UserService {
	public List<User> findAll();

	public User findByUsernameEquals(String username);
	
	public User findByUsernameAndStatusEquals(String username, Integer status);

	public User findByEmailEquals(String email);

	public User findByPhoneNumberEquals(String phoneNumber);

	public List<User> getAdministrators();
	
	User getCustomerById(Integer id);

	User updateCustomerById(User newUser);

	User saveCustomer(User newUser);
}
