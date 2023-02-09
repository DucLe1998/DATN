package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.BillDetail;
import com.example.demo.entities.Product;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
//	@Query("select bd from BillDetail bd where bd.")
//	List<BillDetail> findAllByUser(User user);
	List<BillDetail> findBillDetailByBillId(@Param("bill_id") Long bill_id);

	@Query(value = "SELECT * FROM billdetail_tbl WHERE bill_id =  ?1", nativeQuery = true)
	List<BillDetail> listBillDetailByBillId(@Param("bill_id") Long bill_id);

	List<BillDetail> findByBillId(Long bill_id);

	@Query(value = "SELECT SUM(price) FROM bill_detail WHERE bill_id =  ?1", nativeQuery = true)
	long totalMoney(Long billId);

	@Query("select bd.product.id FROM BillDetail bd WHERE bd.bill.status=1 and bd.product.product.status=1 group by bd.product.id order by sum(bd.quantity) desc")
	List<Long> findTop10ProductByBuyQuantity();

	@Query("select bd.product.product FROM BillDetail bd WHERE bd.bill.status=1 group by bd.product.product.id order by sum(bd.quantity) desc  ")
	Page<Product> findProductByBuyQuantity2(Pageable pageable);

	@Query("FROM Product p where p in (select pd.product from ProductDetail pd where pd in (select bd.product from BillDetail bd where bd.bill =(from Bill b where b.status = 1)))")
	Page<Product> findProductByBuyQuantity(Pageable pageable);

	@Query("select sum(bd.quantity)  FROM BillDetail bd where bd.product.product = ?1")
	Integer findQuantityByProduct(Product product);
}
