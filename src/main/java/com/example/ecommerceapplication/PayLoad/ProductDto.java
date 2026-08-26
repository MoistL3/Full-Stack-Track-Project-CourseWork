package com.example.ecommerceapplication.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {
    private Long productId;
    private String productName ;
    private String image;
    private String description;
    private Integer quantity;
    private Double price ;
    private Double specialPrice ;
    private Double discount ;
    private CategoryDto category;



}
