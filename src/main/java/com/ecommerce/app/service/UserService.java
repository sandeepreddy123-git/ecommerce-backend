package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.AuthenticationFailed;
import com.ecommerce.app.Exceptions.CustomException;
import com.ecommerce.app.Repository.UserRepo;
import com.ecommerce.app.dto.user.*;
import com.ecommerce.app.model.AuthenticationToken;
import com.ecommerce.app.model.User;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticationService authenticationService;
    @Transactional
    public ResponseDto signUp(SignupDto signupDto) throws NoSuchAlgorithmException {
        if(Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("User Already Exists");
        }
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(encryptedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = new User(signupDto.getEmail(),signupDto.getFirstName(),signupDto.getLastName(),encryptedPassword);
        userRepo.save(user);
        AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveToken(authenticationToken);
        ResponseDto responseDto = new ResponseDto("success","User Created Successfully");
        return responseDto;
    }

    private String hashPassword(String encryptedPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(encryptedPassword.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SigninDto singIn(SigninInput signinInput) throws NoSuchAlgorithmException {
        User user = userRepo.findByEmail(signinInput.getEmail());
        if(Objects.isNull(user)) {
            throw new AuthenticationFailed("User is not valid");
        }
       if(!user.getPassword().equals(hashPassword(signinInput.getPassword()))) {
            throw new AuthenticationFailed("Wrong Password");
        }
       AuthenticationToken token = authenticationService.getToken(user);

       if(Objects.isNull(token)) {
           throw new CustomException(("Token is not present"));
       }
       return new SigninDto("Success", token.getToken());
    }

    public void updateUSer(UpdateUserDto updateUserDto) throws NoSuchAlgorithmException {
        User user = userRepo.findByEmail(updateUserDto.getEmail());
        if(Objects.isNull(user)) {
            throw new AuthenticationFailed("User doesn't exists");
        }
        if(!user.getPassword().equals(hashPassword(updateUserDto.getOldPassword()))) {
            throw new AuthenticationFailed("Incorrect Old Password entered");
        }
        user.setPassword(hashPassword(updateUserDto.getNewPassword()));
        userRepo.save(user);
    }
}
