package com.example.ecommerceapplication.service;

import com.example.ecommerceapplication.PayLoad.PageRequestDto;
import com.example.ecommerceapplication.PayLoad.ProductDto;
import com.example.ecommerceapplication.PayLoad.ProductResponse;
import com.example.ecommerceapplication.Response.PageResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public interface ProductService {
    ProductDto addProduct(ProductDto productDto , Long categoryId);
    PageResponseDto<Object> getAllProducts(PageRequestDto request) ;
    PageResponseDto<Object> getAllProductsByCategory(PageRequestDto request ,Long categoryId) ;
    PageResponseDto<Object> getAllProductsByKeyWord(Pageable pageable, String keyWord) ;
    ProductDto updateProduct(ProductDto productDto , Long productToUpdateId );
    void deleteProduct(Long productId);
    ProductDto updateImage(Long productId , MultipartFile image) throws IOException;

}
