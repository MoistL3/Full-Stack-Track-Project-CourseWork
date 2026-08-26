package com.example.ecommerceapplication.service;

import com.example.ecommerceapplication.PayLoad.CategoryDto;
import com.example.ecommerceapplication.PayLoad.CategoryResponse;
import com.example.ecommerceapplication.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize, String sortBy , String sortOrder);

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto deleteCategory(Long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
}
