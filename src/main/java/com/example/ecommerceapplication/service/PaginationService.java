package com.example.ecommerceapplication.service;

import com.example.ecommerceapplication.PayLoad.PageRequestDto;
import com.example.ecommerceapplication.Response.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PaginationService {
   public Pageable buildPageable(PageRequestDto pageRequest) {
       Sort sort = Sort.by(pageRequest.getSortDirection(), pageRequest.getSortBy());
       return PageRequest.of(pageRequest.getPage(), pageRequest.getSize(), sort);
   }
   public <E, D> PageResponseDto<D> toPageResponse(Page<E> entityPage, Function<E, D> mapper) {
        Page<D> dtoPage = entityPage.map(mapper);
        return PageResponseDto.from(dtoPage);
    }
}
