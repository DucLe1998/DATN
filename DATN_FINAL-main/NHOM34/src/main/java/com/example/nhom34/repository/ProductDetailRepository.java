package com.example.nhom34.repository;

import com.example.nhom34.entities.Product;
import com.example.nhom34.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {

    @Query("select distinct pd.color from ProductDetail pd where pd.product.id = :productId")
    List<String> findColorByProductId(long productId);

    @Query("select distinct pd.size from ProductDetail pd where pd.product.id = :productId and pd.color like :color and pd.quantity > 0")
    List<String> findSizeByProductId(long productId, String color);

    ProductDetail findBySizeAndColor(String size, String color);

}
