package com.example.ecommerceapplication.Repository;

import com.example.ecommerceapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
    boolean existsByCategoryName(String categoryName);
}
