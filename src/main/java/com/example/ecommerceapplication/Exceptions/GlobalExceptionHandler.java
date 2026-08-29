package com.example.ecommerceapplication.Exceptions;

import com.example.ecommerceapplication.Response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultExceptionHandler(Exception e){

        log.error("An unexpected error occurred" , e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false , "An unexpected error occurred. Please try again later.")) ;
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        Map<String , String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = (( FieldError) err).getField() ;
            String message  = err.getDefaultMessage();
            response.put(fieldName , message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false ,
                "couldn't fulfil your request" ,response));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> ResourceNotfoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false , message));
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<Object> ApiExceptionHandler(APIException e){
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(false , message ));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Object> authorizationDeniedExceptionHandler(AuthorizationDeniedException e){
       log.error("Unauthorized behaviour" , e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(false ,"You might not Have access" , e.getMessage())) ;
    }
}
