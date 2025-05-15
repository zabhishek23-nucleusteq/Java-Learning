package com.example.UserProductApplication.Exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
