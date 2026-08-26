package com.example.ecommerceapplication.Exceptions;

public class APIException extends RuntimeException{
    public APIException(){}
    public APIException(String message){
        super(message);
    }
}
