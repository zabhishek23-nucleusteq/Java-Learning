package com.example.UserProductApplication.Exception;

public class InvalidProductException extends RuntimeException{
    public InvalidProductException(String message)
    {
        super(message);
    }
}
