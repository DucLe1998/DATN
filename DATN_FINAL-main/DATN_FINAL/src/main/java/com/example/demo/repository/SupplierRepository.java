package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	@Query("select o from Supplier o where o.status=1")
	List<Supplier> getAllSupplierPresent();
	
	Supplier findByProviderName(String providerName);

	Supplier findByPhonerNumber(String phonerNumber);
}
