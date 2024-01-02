package com.ecommerce.app.Exceptions;

public class AuthenticationFailed extends IllegalArgumentException{
    public AuthenticationFailed(String msg) {
        super(msg);
    }
}
