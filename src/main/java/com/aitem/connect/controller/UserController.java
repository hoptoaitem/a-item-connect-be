package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AddressRepository;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.response.ProfileResponse;
import com.aitem.connect.enums.ProfileType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User Profile API")
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {


    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AuthenticationRepository authenticationRepository;

    private UserController(
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired UserRepository userRepository,
            @Autowired AddressRepository addressRepository) {
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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
        User user = userRepository.findById(authentication.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getProfileType().equals(ProfileType.SUPER.name())) {
            return userRepository.findByRole(ProfileType.ADMIN.name());
        } else {
            return null;            
        }
    }
}
