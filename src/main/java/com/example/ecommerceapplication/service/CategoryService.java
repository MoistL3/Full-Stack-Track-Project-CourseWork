package com.example.ecommerceapplication.service;

import com.example.ecommerceapplication.PayLoad.CategoryDto;
import com.example.ecommerceapplication.PayLoad.PageRequestDto;
import com.example.ecommerceapplication.Response.PageResponseDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    PageResponseDto<Object> getAllCategories(PageRequestDto PageRequestDto);

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto deleteCategory(Long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
}
