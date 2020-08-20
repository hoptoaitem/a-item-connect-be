package com.aitem.connect.controller;

import com.aitem.connect.model.Authentication;
import com.aitem.connect.model.ItemModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.AuthenticationRepository;
import com.aitem.connect.repository.UserRepository;
import com.aitem.connect.request.ItemRequest;
import com.aitem.connect.response.PictureResponse;
import com.aitem.connect.service.implementition.ItemService;
import com.aitem.connect.service.implementition.PictureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Api(tags = "picture API")
@CrossOrigin
@RestController
@RequestMapping("/")
public class PictureController {


    private PictureService service;
    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;


    private PictureController(
            @Autowired PictureService service,
            @Autowired AuthenticationRepository authenticationRepository,
            @Autowired UserRepository userRepository
    ) {
        this.service = service;
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }


    @ApiOperation(value = "get the picture details")
    @GetMapping(path = "/pictures/{picture_id}", consumes = "application/json", produces = "application/json")
    public PictureResponse getPicture(@RequestHeader("api-key-token") String key,
                                      @PathVariable("picture_id") String pictureId) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.getPictureDetails(pictureId, user);
    }

    // TODO : pass as list
    @PostMapping(path = "/pictures")
    public UUID upload(@RequestHeader("api-key-token") String key,
                       @RequestParam("file") MultipartFile file) {

        // TODO: move this to common location
        Authentication authentication = authenticationRepository.findByToken(key);
        User user = userRepository.findById(authentication.getUserId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found"));

        return service.upload(file, user);
    }
}
