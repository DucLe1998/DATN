package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AccountDTO;
import com.example.demo.entities.User;
import com.example.demo.entities.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;

@Service
public class AccountDTOService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	public List<AccountDTO> getAllAccountDTO() {
		return ((List<User>) userRepository.findAll()).stream().map(this::convertDataIntoDTO)
				.collect(Collectors.toList());
	}

	private AccountDTO convertDataIntoDTO(User accountData) {
		AccountDTO dto = new AccountDTO();
		dto.setId(accountData.getId());
		dto.setUsername(accountData.getUsername());
		dto.setPassword(accountData.getPassword());
		dto.setCreateDay(accountData.getCreateDay());
		dto.setStatus(accountData.getStatus());
		Optional<UserRole> userRole = userRoleRepository.findById(accountData.getId());
		dto.setRole(userRole.get().getRole().getId());
		return dto;
	}

}
