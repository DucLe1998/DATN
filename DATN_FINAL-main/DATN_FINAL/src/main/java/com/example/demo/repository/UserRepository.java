package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findUsersByStatus(Integer status);
	@Query("SELECT ur.user  FROM UserRole ur WHERE ur.role.id = (2, 3)")
	List<User> getAdministrators();

	@Query("SELECT ur.user  FROM UserRole ur WHERE ur.role.id = 1")
	List<User> getCustomers();

	public List<User> findByIdEquals(Integer id);
	
	public User findByUsernameAndStatusEquals(String username, Integer status);

	public User findByUsernameEquals(String username);

	public User findByEmailEquals(String email);
     
	public User findByPhoneNumberEquals(String phoneNumber);
	
	public User findByUsernameAndEmailEquals(String username, String email);

}
