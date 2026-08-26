package com.example.ecommerceapplication.controller;

import com.example.ecommerceapplication.PayLoad.PageRequestDto;
import com.example.ecommerceapplication.Response.ApiResponse;
import com.example.ecommerceapplication.PayLoad.ProductDto;
import com.example.ecommerceapplication.config.AppConstants;
import com.example.ecommerceapplication.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {


    private ProductService productService ;

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping("/admin/{categoryId}/products")
    public ResponseEntity<Object> addProduct (@Valid @RequestBody ProductDto productDto
            , @PathVariable Long categoryId){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true , "product added successfully", productService.addProduct(productDto , categoryId)));
    }


    @GetMapping("/public/products")
    public ResponseEntity<Object> getAllProducts(@RequestParam (name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber ,
                                                 @RequestParam (name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize ,
                                                 @RequestParam (name = "sortBy" , defaultValue = AppConstants.SORT_BY ,required = false) String sortBy ,
                                                 @RequestParam (name = "sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder){
        PageRequestDto pageRequestDto = new PageRequestDto(pageNumber , pageSize , sortBy ,sortOrder);
        return ResponseEntity
                .ok()
                .body(new ApiResponse<>(true , "all products" , productService.getAllProducts(pageRequestDto)));
    }


    @GetMapping("/public/{categoryId}/products")
    public ResponseEntity<Object> getAllProductsByCategory(
            @RequestParam (name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber ,
            @RequestParam (name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize ,
            @RequestParam (name = "sortBy" , defaultValue = AppConstants.SORT_BY ,required = false) String sortBy ,
            @RequestParam (name = "sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder,
            @PathVariable Long categoryId){

        PageRequestDto pageRequestDto = new PageRequestDto(pageNumber , pageSize , sortBy ,sortOrder);

        return ResponseEntity
                .ok().body(new ApiResponse<>(true , "products by certain category", productService.getAllProductsByCategory(pageRequestDto,categoryId)));
    }


    @GetMapping("/public/products/{keyWord}")
    public ResponseEntity<Object> getAllProductsByKeyWord(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable ,
            @PathVariable String keyWord
    ){

       return ResponseEntity.ok()
               .body(new ApiResponse<>(true , "search results"
                       , productService.getAllProductsByKeyWord(pageable,keyWord))) ;

    }



    @PutMapping("admin/products/{productToUpdateId}")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody ProductDto productDto , @PathVariable Long productToUpdateId){
                    return ResponseEntity.ok()
                            .body(new ApiResponse<>(true , "Product Updated Successfully" , productService.updateProduct(productDto, productToUpdateId)));
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(true , "product deleted successfully")) ;

    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable Long productId ,
                                              @RequestParam("image") MultipartFile Image) throws IOException {

        ProductDto updatedProduct = productService.updateImage(productId, Image);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true , "product image updated successfully"));

    }


}
