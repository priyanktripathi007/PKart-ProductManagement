package com.pramati.kart.productManagement.repository;

import com.pramati.kart.productManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

 @Query("SELECT m FROM Product m join Category  c on c.id=m.categoryId WHERE  CONCAT(c.name, '', m.name, '', m.description, '', c.description) LIKE %:searchKey%")
    List<Product> findProduct(@Param("searchKey") String searchKey);
}
