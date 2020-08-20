package com.aitem.connect.service;

import com.aitem.connect.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface Picture {

    public UUID upload(MultipartFile request, User user);
}
