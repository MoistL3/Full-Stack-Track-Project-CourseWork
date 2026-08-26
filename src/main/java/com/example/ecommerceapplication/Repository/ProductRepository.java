package com.example.ecommerceapplication.Repository;

import com.example.ecommerceapplication.model.Category;
import com.example.ecommerceapplication.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product , Long>{
    boolean existsByProductName(String productName);
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByProductNameLikeIgnoreCase(String productName, Pageable pageable);
    boolean existsByProductNameIgnoreCaseAndCategory(String productName , Category category);

    Page <Product> findAllByCategory(Category category, Pageable pageable);

}


