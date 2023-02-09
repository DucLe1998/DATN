package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import com.example.demo.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	UserRoleRepository userRoleDAO;
	@Autowired
	UserRepository userDAO;

	public List<UserRole> findAll() {
		return userRoleDAO.findAll();
	}

	public List<UserRole> findAuthoritiesOfAdministrators() {
		List<User> users = userDAO.getAdministrators();
		return userRoleDAO.authoritiesOf(users);
	}
}
