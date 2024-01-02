package com.ecommerce.app.dto.user;

public class SigninInput {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SigninInput(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
