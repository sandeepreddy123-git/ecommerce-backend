package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.AuthenticationFailed;
import com.ecommerce.app.Repository.AuthenticationTokenRepo;
import com.ecommerce.app.model.AuthenticationToken;
import com.ecommerce.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationTokenRepo authenticationTokenRepo;
    public void saveToken(AuthenticationToken authenticationToken) {
        authenticationTokenRepo.save(authenticationToken);
    }

    public void Authenticate(String token) throws AuthenticationFailed {
        if(Objects.isNull(token)) {
            throw new AuthenticationFailed("token not present");
        }
        if(Objects.isNull(getUser(token))) {
            throw new AuthenticationFailed("Invalid Token");
        }

    }

    public User getUser(String token) {
        AuthenticationToken authToken = authenticationTokenRepo.findByToken(token);
        if(Objects.isNull(authToken)) {
            return null;
        }
        return authToken.getUser();
    }

    public AuthenticationToken getToken(User user) {
        return authenticationTokenRepo.findByUser(user);
    }
}
