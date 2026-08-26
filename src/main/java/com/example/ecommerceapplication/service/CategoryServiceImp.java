package com.example.ecommerceapplication.service;

import com.example.ecommerceapplication.Exceptions.APIException;
import com.example.ecommerceapplication.Exceptions.ResourceNotFoundException;
import com.example.ecommerceapplication.PayLoad.CategoryDto;
import com.example.ecommerceapplication.PayLoad.CategoryResponse;
import com.example.ecommerceapplication.Repository.CategoryRepository;
import com.example.ecommerceapplication.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;


    public CategoryServiceImp(CategoryRepository categoryRepository, ModelMapper modelMapper){

        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper ;
    }


    @Override
    public CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize ,String sortBy , String sortOrder) {
        Sort sortByAnOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber , pageSize , sortByAnOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("you haven't added any categories yet");
        }


        List<CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category , CategoryDto.class))
                .toList() ;
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtos);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;

    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByCategoryName(categoryDto.getCategoryName())) {
            throw new APIException("category you want to creat already exist");
        }

        Category category = modelMapper.map(categoryDto, Category.class);
        Category category1 =  categoryRepository.save(category);
        return  modelMapper.map(category1, CategoryDto.class);
    }


    @Override
    public CategoryDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category ,CategoryDto.class);


    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto , Long categoryId) {

       Optional<Category> categoryToUpdateOptional = categoryRepository.findById(categoryId);
        Category savedCategory = categoryToUpdateOptional.orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" ,categoryId));

       savedCategory.setCategoryName(modelMapper.map(categoryDto , Category.class).getCategoryName());
       Category updatedCategory = categoryRepository.save(savedCategory);

       return modelMapper.map(savedCategory , CategoryDto.class);

    }




}
