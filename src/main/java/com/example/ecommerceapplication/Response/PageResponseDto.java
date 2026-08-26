package com.example.ecommerceapplication.Response;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
@Data
public class PageResponseDto<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public  static <T> PageResponseDto<T> from(Page<T> page){
        PageResponseDto<T> response =new PageResponseDto<>() ;
        response.setContent(page.getContent());
        response.setPageNumber(page.getNumber());
        response.setLast(page.isLast());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        return response ; 
    }
}
