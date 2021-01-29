package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AddressRepository;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.response.ProfileResponse;
import com.aitem.connect.request.UpdateUserStatusRequest;
import com.aitem.connect.helper.Crypt;
import com.aitem.connect.enums.ProfileType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@Api(tags = "User Profile API")
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AuthenticationRepository authenticationRepository;
    private Crypt crypt;

    private UserController(
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired UserRepository userRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired Crypt crypt) {
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.crypt = crypt;
    }

    @ApiOperation(value = "Get users")
    @GetMapping(path = "/users", consumes = "application/json", produces = "application/json")
    public List<User> getUsers(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (user.getProfileType().equals(ProfileType.SUPER.name())) {
            List<User> users = userRepository.findAll();
            List<User> results = new ArrayList<>();

            for(User usr : users) {
                if(!usr.getProfileType().equals(ProfileType.SUPER.name()) && !usr.getProfileType().equals(ProfileType.ADMIN.name())) {
                    results.add(usr);
                }
            }
            return results;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Get user profile")
    @GetMapping(path = "/profiles", consumes = "application/json", produces = "application/json")
    public ProfileResponse getProfile(@RequestHeader("api-key-token") String key) {

        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setUsername(user.getUsername());
        profileResponse.setProfileType(user.getProfileType());

        profileResponse.setAddress(addressRepository.findById(user.getAddressId()).
                orElseThrow(IllegalArgumentException::new));

        profileResponse.setFirstName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setPhone(user.getPhone());
        profileResponse.setStatus(user.getStatus());
        profileResponse.setDeviceId(user.getDeviceId());
        profileResponse.setCreatedAt(user.getCreatedAt());
        profileResponse.setModifiedAt(user.getModifiedAt());
        profileResponse.setCreatedBy(user.getCreatedBy());
        profileResponse.setModifiedBy(user.getCreatedBy());

        return profileResponse;
    }

    @ApiOperation(value = "Get admins")
    @GetMapping(path = "/admins", consumes = "application/json", produces = "application/json")
    public List<User> getAdmins(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (user.getProfileType().equals(ProfileType.SUPER.name())) {
            return userRepository.findByProfileType(ProfileType.ADMIN.name());
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Get eventUsers")
    @GetMapping(path = "/eventUsers", consumes = "application/json", produces = "application/json")
    public List<User> getEventUsers(@RequestHeader("api-key-token") String key) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (user.getProfileType().equals(ProfileType.SUPER.name())) {
            List<User> users = userRepository.findByProfileType(ProfileType.SHOPPER.name());
            List<User> results = new ArrayList<User>();

            for (User usr : users) {
                if(crypt.decrypt(usr.getIv(), usr.getSalt(), usr.getPass()).equals("testEventUser")) {
                    results.add(usr);
                }
            }

            return usr;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "Update admin status")
    @PutMapping(path = "/{admin-id}/updateStatus", consumes = "application/json", produces = "application/json")
    public User updateStatus(@RequestHeader("api-key-token") String key, @PathVariable("admin-id") String adminId, @RequestBody UpdateUserStatusRequest request) {
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found."));

        if(user.getProfileType().equals(ProfileType.SUPER.name())) {
            User admin = userRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("Admin not found."));
            admin.setStatus(request.getStatus());
            return userRepository.save(admin);
        } else {
            return null;
        }
    }
}
