package com.example.ecommerceapplication.controller;

import com.example.ecommerceapplication.Response.ApiResponse;
import com.example.ecommerceapplication.PayLoad.CategoryDto;
import com.example.ecommerceapplication.PayLoad.CategoryResponse;
import com.example.ecommerceapplication.config.AppConstants;
import com.example.ecommerceapplication.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api")

public class CategoryController {

    private CategoryService categoryService ;

    CategoryController(CategoryService categoryService){
        this.categoryService= categoryService;
    }
    @GetMapping
    public ResponseEntity<Object> echoMessage(@RequestParam(name =  "message" )String message ){
        return  ResponseEntity.ok()
                .body(new ApiResponse<>(true ,"echoed message" , message));

    }

    @GetMapping("/public/categories")
    public ResponseEntity<Object> getAllCategories(
            @RequestParam (name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber ,
            @RequestParam (name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize ,
            @RequestParam (name = "sortBy" , defaultValue = AppConstants.SORT_BY ,required = false) String sortBy ,
            @RequestParam (name = "sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder
            ){

        CategoryResponse categoryResponse =categoryService.getAllCategories(pageNumber , pageSize , sortBy , sortOrder);
        return ResponseEntity.ok()
              .body(new ApiResponse<>(true , "all categories" , categoryResponse ));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategoryDto  =  categoryService.addCategory(categoryDto);
       return ResponseEntity.status(201).body(new ApiResponse<>(true , "category added successfully",savedCategoryDto));
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId){
         CategoryDto deletedCategoryDto =   categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(true , "Category deleted successfully" , deletedCategoryDto));


      }


    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){

            CategoryDto updatedCategoryDto  = categoryService.updateCategory(categoryDto , categoryId);
            return ResponseEntity.ok().body(new ApiResponse<>( true , "Category with updated successfully " , updatedCategoryDto ));
    }


}

