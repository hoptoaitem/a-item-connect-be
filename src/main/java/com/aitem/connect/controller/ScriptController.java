package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.script.LoadZip1;
import com.aitem.connect.script.ZipDao;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Item")
@CrossOrigin
@RestController
@RequestMapping("/script")
public class ScriptController {


    private ZipDao zipDao;
    private LoadZip1 loadZip1;
    private UserRepository userRepository;
    private AuthenticationRepository authenticationRepository;


    private ScriptController(
            @Autowired LoadZip1 loadZip1,
            @Autowired ZipDao zipDao,
            @Autowired UserRepository userRepository,
            @Autowired AuthenticationRepository authenticationRepository
    ) {
        this.loadZip1 = loadZip1;
        this.zipDao = zipDao;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }


    @PostMapping(path = "/mapzip", consumes = "application/json", produces = "application/json")
    public UUID mapZip(@RequestHeader("api-key-token") String key) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));


        zipDao.mapZip();

        return UUID.fromString(user.getId());
    }

    @PostMapping(path = "/loadzip", consumes = "application/json", produces = "application/json")
    public UUID loadZip(@RequestHeader("api-key-token") String key) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));


        loadZip1.loadZip();

        return UUID.fromString(user.getId());
    }

    @PostMapping(path = "/loadzipdriver", consumes = "application/json", produces = "application/json")
    public UUID loadZipDriver(@RequestHeader("api-key-token") String key) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));


        zipDao.loadZipDriver();

        return UUID.fromString(user.getId());
    }

    @PostMapping(path = "/loadzipstore", consumes = "application/json", produces = "application/json")
    public UUID loadZipStore(@RequestHeader("api-key-token") String key) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));


        zipDao.loadZipStore();

        return UUID.fromString(user.getId());
    }
}
