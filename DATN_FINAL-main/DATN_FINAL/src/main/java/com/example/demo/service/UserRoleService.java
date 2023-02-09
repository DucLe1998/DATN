package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.UserRole;

public interface UserRoleService {
	public List<UserRole> findAll();

	public List<UserRole> findAuthoritiesOfAdministrators();
}