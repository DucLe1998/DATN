package com.example.demo.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Interaction;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long>{
	@Query("select i.product.id from Interaction i where i.likeStatus = 1 and (i.rating = 4 or i.rating = 5) and i.product.status =1"
			+ "group by i.product.id order by count(i.likeStatus) DESC")
	List<Long> findByTopInteractions();
	
	@Query("select i.product.id from Interaction i where i.likeStatus = 1 "
			+ "group by i.product.id order by count(i.likeStatus) DESC")
	List<Long> findByTopLike();
	
	@Query("select i.product.id from Interaction i where i.comment is not null "
			+ "group by i.product.id order by count(i.comment) DESC")
	List<Long> findByTopComment();
	
	@Query("select i.product.id from Interaction i "
			+ "group by i.product.id having avg(i.rating)>=4 order by avg(i.rating) DESC")
	List<Long> findByTopRating();
	
	@Query("select i.product.id from Interaction i where i.likeStatus = 1 and i.user = ?1")
	List<Long> findByUserLike(User user);
	
	@Query("select i.product from Interaction i where i.likeStatus = 1 and i.user = ?1")
	Page<Product> findByUserLike(User user,Pageable pageable);
	
	@Query("select i.product from Interaction i where i.likeStatus = 1 and i.user = ?1 and i.product.status =1")
	List<Product> findByUserLike2(User user);
	
	Interaction findByUserAndProduct(User user,Product product);
	
	List<Interaction> findByProduct(Product product);
	
	Interaction findByUserAndProductAndLikeStatus(User user,Product product,Integer lstt);
	
	@Query("select avg(i.rating) from Interaction i where i.product.id =?1")
	Double findProductRatingAvg(Long id);
}
