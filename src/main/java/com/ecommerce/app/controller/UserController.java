package com.ecommerce.app.controller;

import com.ecommerce.app.dto.user.*;
import com.ecommerce.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) throws NoSuchAlgorithmException {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SigninDto singIn(@RequestBody SigninInput signinInput) throws NoSuchAlgorithmException {
        return userService.singIn(signinInput);
    }

    @PostMapping("/update/")
    public void updateUser(@RequestBody UpdateUserDto updateUserDto) throws NoSuchAlgorithmException {
        userService.updateUSer(updateUserDto);
    }
}
