package com.aitem.connect.service;

import com.aitem.connect.model.User;
import com.aitem.connect.response.PictureResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface Picture {

    PictureResponse upload(MultipartFile request, User user);
}
