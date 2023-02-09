package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Bill;
import com.example.demo.entities.User;


@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
	
	@Query("select b from Bill b where b.customer.id=?1")
	List<Bill> findBillByUserId(Integer user_id);
	
	List<Bill> findByCustomer(User user);

	public List<Bill> findByStatus(Integer status);

	@Query("select sum(b.total)  FROM Bill b where b.createDay BETWEEN ?1 AND ?2")
	double findBillByCreateDay(Date date1, Date date2);
	
	@Query("select count(b.id)  FROM Bill b where b.createDay BETWEEN ?1 AND ?2")
	int findBillSoByCreateDay(Date date1, Date date2);
}
