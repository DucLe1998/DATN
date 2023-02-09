package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	@Query("SELECT ur FROM UserRole ur WHERE ur.user = ?1")
	List<UserRole> authoritiesOf(List<User> users);

	@Query("Select u FROM UserRole u WHERE u.role.id = 2 or u.role.id = 3")
	List<UserRole> findAllAdminOrEmployees();

	@Query("Select u FROM UserRole u WHERE u.role.id = 1")
	List<UserRole> findAllUsers();

}
