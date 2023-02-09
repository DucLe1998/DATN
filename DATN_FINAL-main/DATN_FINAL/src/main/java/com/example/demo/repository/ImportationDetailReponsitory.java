package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Importation;
import com.example.demo.entities.ImportationDetail;

@Repository
public interface ImportationDetailReponsitory extends JpaRepository<ImportationDetail,Long >{

	@Query("select o from ImportationDetail o where o.importation=?1")
	List<ImportationDetail> findByImportationId(Importation id);
}
