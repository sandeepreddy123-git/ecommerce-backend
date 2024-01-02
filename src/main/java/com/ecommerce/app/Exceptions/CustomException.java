package com.ecommerce.app.Exceptions;

public class CustomException extends IllegalArgumentException{
    public CustomException(String msg) {
        super(msg);
    }
}
