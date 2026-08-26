package com.example.ecommerceapplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor



public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id ;

    private String description ;
    private String productName ;
    @NotNull
    @Min(value =  0 ,message = "quantity cannot be negative")
    private Integer quantity ;
    private Double price;
    private Double specialPrice ;
    private Double discount ;
    private String image ;

    @ManyToOne
    @JoinColumn(name =  "category_id")
    private Category category ;

}
