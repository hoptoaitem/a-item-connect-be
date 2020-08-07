package com.aitem.connect.controller;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.helper.Crypt;
import com.aitem.connect.helper.CryptData;
import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.LoginRequest;
import com.aitem.connect.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private UserRepository userRepository;
    private AuthenticationRepository authenticationRepository;
    private Crypt crypt;

    private LoginController(@Autowired Crypt crypt,
                            @Autowired AuthenticationRepository authenticationRepository,
                            @Autowired UserRepository userRepository) {
        this.crypt = crypt;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public LoginResponse login(@RequestBody LoginRequest request) {

        LoginResponse response = new LoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        try {
            // String iv, String salt, String password
            if (password.equals(crypt.decrypt(user.getIv(), user.getSalt(),
                    user.getPass()))) {
                response.setProfileType(user.getProfileType());
                Authentication authentication
                        = authenticationRepository.findByUserId(user.getId());
                if (authentication == null) {
                    Authentication a = new Authentication();
                    a.setToken(crypt.encrypt(user.getUsername()).getCipherText());
                    a.setUserId(user.getId());
                    authenticationRepository.save(a);
                }
                response.setAuthToken(authentication.getToken());

                return response;
            }
        } catch (Exception e) {

        }
        throw new IllegalArgumentException();
    }

    @PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
    public LoginResponse createUser(@RequestBody LoginRequest request) {


        CryptData a = crypt.encrypt(request.getPassword());

        User user = new User();
        user.setIv(new String(a.getIv()));
        user.setSalt(new String(a.getSalt()));
        user.setPass(a.getCipherText());
        user.setUsername(request.getUsername());
        user.setProfileType(ProfileType.NOT_DECIDED.getCode());

        userRepository.save(user);
        LoginResponse response = new LoginResponse();
        response.setUsername(request.getUsername());

        return response;
    }
}
