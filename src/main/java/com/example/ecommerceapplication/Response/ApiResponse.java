package com.example.ecommerceapplication.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T> {
    private boolean success ;
    private String message ;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;



    public ApiResponse(boolean success , String message){
        this .success = success ;
        this .message = message;
    }


}
