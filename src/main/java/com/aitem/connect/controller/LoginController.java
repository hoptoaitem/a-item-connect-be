package com.aitem.connect.controller;

import com.aitem.connect.enums.ProfileType;
import com.aitem.connect.enums.UserStatus;
import com.aitem.connect.helper.Crypt;
import com.aitem.connect.helper.CryptData;
import com.aitem.connect.mapper.AddressMapper;
import com.aitem.connect.model.AddressModel;
import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AddressRepository;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.AddressRequest;
import com.aitem.connect.request.LoginRequest;
import com.aitem.connect.request.UserRequest;
import com.aitem.connect.response.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@Api(tags = "Login API")
@CrossOrigin
@RestController
public class LoginController {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AuthenticationRepository authenticationRepository;
    private Crypt crypt;

    private LoginController(@Autowired Crypt crypt,
                            @Autowired AuthenticationRepository authenticationRepository,
                            @Autowired UserRepository userRepository,
                            @Autowired AddressRepository addressRepository) {
        this.crypt = crypt;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @ApiOperation(value = "Login for the application")
    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public LoginResponse login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        if(user.getStatus().equals(UserStatus.NOT_APPROVED.name())) {
            throw new IllegalArgumentException("User not allowed.");   
        }

        if (user.getProfileType().equals(ProfileType.DRIVER.name())
                && StringUtils.isNotEmpty(request.getDeviceId())) {
            user.setDeviceId(request.getDeviceId());
            userRepository.save(user);
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
                    a.setId(UUID.randomUUID().toString());
                    a.setToken(crypt.encrypt(user.getUsername() + "-" +
                            LocalDate.now() + "-" + UUID.randomUUID().toString()).getCipherText());
                    a.setUserId(user.getId());
                    authentication = authenticationRepository.save(a);
                }
                response.setAuthToken(authentication.getToken());
                response.setProfileType(user.getProfileType());

                return response;
            }
        } catch (Exception e) {
            System.out.println("Exception on proceesing user");
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
        throw new IllegalArgumentException();
    }

    @ApiOperation(value = "Login for the application")
    @PostMapping(path = "/loginForEvent", consumes = "application/json", produces = "application/json")
    public LoginResponse loginForEvent(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByEmail(username);
        if (user == null) {
            CryptData a = crypt.encrypt(password);
            user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setIv(new String(a.getIv()));
            user.setSalt(new String(a.getSalt()));
            user.setPass(a.getCipherText());
            user.setUsername("Event User");
            user.setStatus(UserStatus.APPROVED.name());
            user.setProfileType(ProfileType.SHOPPER.name());
            user.setFirstName("Event");
            user.setLastName("User");
            user.setEmail(username);
            user.setPhone("123");
            AddressModel addressModel = new AddressModel();
            addressModel.setId(UUID.randomUUID().toString());
            addressRepository.save(addressModel);
            user.setAddressId(addressModel.getId());
            userRepository.save(user);
            response.setProfileType(user.getProfileType());
            Authentication authentication
                    = authenticationRepository.findByUserId(user.getId());
            if (authentication == null) {
                Authentication b = new Authentication();
                b.setId(UUID.randomUUID().toString());
                b.setToken(crypt.encrypt(user.getUsername() + "-" +
                        LocalDate.now() + "-" + UUID.randomUUID().toString()).getCipherText());
                b.setUserId(user.getId());
                authentication = authenticationRepository.save(b);
            }
            response.setAuthToken(authentication.getToken());
            response.setProfileType(user.getProfileType());

            return response;
        }

        try {
            if (password.equals(crypt.decrypt(user.getIv(), user.getSalt(),
                    user.getPass()))) {
                response.setProfileType(user.getProfileType());
                Authentication authentication
                        = authenticationRepository.findByUserId(user.getId());
                if (authentication == null) {
                    Authentication a = new Authentication();
                    a.setId(UUID.randomUUID().toString());
                    a.setToken(crypt.encrypt(user.getUsername() + "-" +
                            LocalDate.now() + "-" + UUID.randomUUID().toString()).getCipherText());
                    a.setUserId(user.getId());
                    authentication = authenticationRepository.save(a);
                }
                response.setAuthToken(authentication.getToken());
                response.setProfileType(user.getProfileType());

                return response;
            } else {
                CryptData a = crypt.encrypt(password);
                user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setIv(new String(a.getIv()));
                user.setSalt(new String(a.getSalt()));
                user.setPass(a.getCipherText());
                user.setUsername("Event User");
                user.setStatus(UserStatus.APPROVED.name());
                user.setProfileType(ProfileType.SHOPPER.name());
                user.setFirstName("Event");
                user.setLastName("User");
                user.setEmail(username);
                user.setPhone("123");
                AddressModel addressModel = new AddressModel();
                addressModel.setId(UUID.randomUUID().toString());
                addressRepository.save(addressModel);
                user.setAddressId(addressModel.getId());
                userRepository.save(user);
                response.setProfileType(user.getProfileType());
                Authentication authentication
                        = authenticationRepository.findByUserId(user.getId());
                if (authentication == null) {
                    Authentication b = new Authentication();
                    b.setId(UUID.randomUUID().toString());
                    b.setToken(crypt.encrypt(user.getUsername() + "-" +
                            LocalDate.now() + "-" + UUID.randomUUID().toString()).getCipherText());
                    b.setUserId(user.getId());
                    authentication = authenticationRepository.save(b);
                }
                response.setAuthToken(authentication.getToken());
                response.setProfileType(user.getProfileType());

                return response;
            }
        } catch (Exception e) {
            System.out.println("Exception on proceesing user");
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
        throw new IllegalArgumentException();
    }

    @ApiOperation(value = "Create user for the application")
    @PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
    public LoginResponse createUser(@RequestBody UserRequest request) {
        User tempUser = userRepository.findByEmail(request.getEmail());
        if (tempUser != null) {
            throw new IllegalArgumentException("User exists with this email.");
        }

        CryptData a = crypt.encrypt(request.getPassword());
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setIv(new String(a.getIv()));
        user.setSalt(new String(a.getSalt()));
        user.setPass(a.getCipherText());
        user.setUsername(request.getUsername());
        if (request.getProfileType().equals(ProfileType.SHOPPER)) {
            user.setStatus(UserStatus.APPROVED.name());
        } else if(request.getProfileType().equals(ProfileType.ADMIN)) {
           user.setStatus(UserStatus.NOT_APPROVED.name());
        } else {
            user.setStatus(UserStatus.SUBMITTED.name());
        }
        user.setProfileType(request.getProfileType().name());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        AddressRequest address = request.getAddress();
        AddressModel addressModel = AddressMapper.getAddressModel(address);
        addressModel.setId(UUID.randomUUID().toString());
        addressRepository.save(addressModel);
        user.setAddressId(addressModel.getId());

        userRepository.save(user);
        LoginResponse response = new LoginResponse();
        response.setUsername(request.getUsername());

        return response;
    }
}
