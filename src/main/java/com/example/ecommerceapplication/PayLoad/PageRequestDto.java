package com.example.ecommerceapplication.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor

public class PageRequestDto {
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "id";
    private String direction = "ASC";

    public Sort.Direction getSortDirection (){
        return direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
    }

}
