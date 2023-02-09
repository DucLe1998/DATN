package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Delivery;

@Repository
public interface DeliveryRespository extends JpaRepository<Delivery, Long> {

	public Delivery findByBillIdEquals(Long id);

}
