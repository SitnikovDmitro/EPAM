package com.ra.model.repository;

import com.ra.model.entity.ChequeLine;
import com.ra.model.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE title LIKE ?1 AND removed = false;")
    List<Product> findProductsByPattern(String pattern);
}
