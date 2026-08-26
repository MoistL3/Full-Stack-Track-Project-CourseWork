package com.example.ecommerceapplication.service;

import com.example.ecommerceapplication.Exceptions.APIException;
import com.example.ecommerceapplication.Exceptions.ResourceNotFoundException;
import com.example.ecommerceapplication.PayLoad.PageRequestDto;
import com.example.ecommerceapplication.PayLoad.ProductDto;
import com.example.ecommerceapplication.PayLoad.ProductResponse;
import com.example.ecommerceapplication.Repository.CategoryRepository;
import com.example.ecommerceapplication.Repository.ProductRepository;
import com.example.ecommerceapplication.Response.PageResponseDto;
import com.example.ecommerceapplication.model.Category;
import com.example.ecommerceapplication.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Service

public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private ModelMapper productMapper ;
    private CategoryRepository categoryRepository ;
    private FileService fileService ;
    private final PaginationService paginationService ;
    @Value("${project.image}")
    private String path ;

    public ProductServiceImpl(ProductRepository productRepository
            , ModelMapper productMapper
            , CategoryRepository categoryRepository
            , FileService fileService
            , PaginationService paginationService){
        this.productMapper = productMapper ;
        this.productRepository = productRepository;
        this.categoryRepository=categoryRepository ;
        this.fileService = fileService ;
        this.paginationService = paginationService ;

    }

    @Override
    public ProductDto addProduct(ProductDto productDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("category" , "categoryId" , categoryId));
        if(productRepository.existsByProductNameIgnoreCaseAndCategory(productDto.getProductName() , category)){
            throw new APIException("Product with Name "+ productDto.getProductName()+"Already exist in category "+category.getCategoryName());
        }

        Product productToAdd = productMapper.map(productDto,Product.class);

        Double specialPrice = productToAdd.getPrice() - (productToAdd.getDiscount() * 0.01 ) * productToAdd.getPrice() ;
        productToAdd.setSpecialPrice(specialPrice);
        productToAdd.setCategory(category);
       if(productToAdd.getImage()==null) { productToAdd.setImage("default.png");}
        Product savedProduct = productRepository.save(productToAdd);
        return productMapper.map(savedProduct , ProductDto.class);

    }

    @Override
    public PageResponseDto<Object> getAllProducts(PageRequestDto request) {
        Pageable pageable = paginationService.buildPageable(request);
        Page <Product> productPage= productRepository.findAll(pageable);
        return paginationService.toPageResponse(productPage, product -> productMapper.map(product , ProductDto.class));


    }


    @Override
    public PageResponseDto<Object> getAllProductsByCategory(PageRequestDto request ,Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cateogry" , "Category ID : " , categoryId));
        Pageable pageable = paginationService.buildPageable(request);
        Page<Product> productsByCategoryPage = productRepository.findAllByCategory(category ,pageable);
        return paginationService.toPageResponse(productsByCategoryPage, product -> productMapper.map(product , ProductDto.class));

      /*  if(!categoryRepository.existsById(categoryId)){ throw new ResourceNotFoundException("Category" , "Category ID" , categoryId); }
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber , pageSize ,sort) ;
        Page<Product> productPage = productRepository.findByCategory_CategoryId(categoryId , pageable);
        List<ProductDto> productsOfCategory =  productPage
                .stream()
                .filter(product -> product.getCategory().getCategoryId().equals(categoryId))
                .map(product -> productMapper.map(product , ProductDto.class))
                .toList() ;
        ProductResponse response = new ProductResponse();
        response.setContent(productsOfCategory);
        response.setLastPage(productPage.isLast());
        response.setPageNumber(productPage.getNumber());
        response.setPageSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        return response ;*/
    }

    @Override
    public PageResponseDto<Object> getAllProductsByKeyWord(Pageable pageable, String keyWord) {

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyWord+'%' , pageable);
        return paginationService.toPageResponse(productPage  , product -> productMapper.map(product , ProductDto.class));

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long productToUpdateId) {
        Product productToUpdate = productRepository.findById(productToUpdateId)
                .orElseThrow(() -> new ResourceNotFoundException("Product" , "Product ID : " , productToUpdateId));


        Category category = categoryRepository.findById(productDto.getCategory().getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category" , "Category ID : " , productDto.getCategory().getCategoryId()));

        productToUpdate.setProductName(productDto.getProductName());
        productToUpdate.setPrice(productDto.getPrice());
        productToUpdate.setImage(productDto.getImage());
        productToUpdate.setCategory(category);
        productToUpdate.setDescription(productDto.getDescription());
        productToUpdate.setQuantity(productDto.getQuantity());

        Double price = productDto.getPrice() != null ? productDto.getPrice() : productToUpdate.getPrice();
        Double discount = productDto.getDiscount() != null ? productDto.getDiscount() : 0.0;
        Double specialPrice = price - (discount * 0.01) * price;
        productToUpdate.setSpecialPrice(specialPrice);

        return productMapper.map(productRepository.save(productToUpdate) , ProductDto.class);



    }

    @Override
    public void deleteProduct(Long productId) {
      Product productToBeDeleted = productRepository.findById(productId)
              .orElseThrow(()-> new APIException("product you want to delete DNE"));
      productRepository.delete(productToBeDeleted);
    }

    @Override
    public ProductDto updateImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Product ID : " , productId));

        String fileName = fileService.uploadImage(path , image);
        productFromDb.setImage(fileName);

        Product savedProduct = productRepository.save(productFromDb);
        return productMapper.map(savedProduct , ProductDto.class);


    }


}
