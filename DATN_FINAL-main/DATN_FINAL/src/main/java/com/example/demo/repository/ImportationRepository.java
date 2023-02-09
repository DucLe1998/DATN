package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Importation;

@Repository
public interface ImportationRepository extends JpaRepository<Importation, Long> {

	@Query("Select o from Importation o where o.status = 1 or o.status = 2")
	List<Importation> findByNoImportAndImportImportation();
}