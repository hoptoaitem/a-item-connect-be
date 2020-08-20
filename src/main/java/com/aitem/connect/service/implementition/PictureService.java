package com.aitem.connect.service.implementition;

import com.aitem.connect.model.PictureModel;
import com.aitem.connect.model.User;
import com.aitem.connect.repository.*;
import com.aitem.connect.response.PictureResponse;
import com.aitem.connect.service.Picture;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class PictureService implements Picture {

    private CartRepository cartRepository;
    private PictureRepository pictureRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;
    private StoreRepository storeRepository;
    private RetailerUserRepository retailerUserRepository;
    private OrderDAO orderDAO;
    private AmazonS3 s3Client;


    private PictureService(
            @Autowired AmazonS3 s3Client,
            @Autowired PictureRepository pictureRepository,
            @Autowired OrderDAO orderDAO,
            @Autowired CartRepository cartRepository,
            @Autowired OrderRepository orderRepository,
            @Autowired OrderDetailRepository orderDetailRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired UserRepository userRepository,
            @Autowired ItemRepository itemRepository,
            @Autowired StoreRepository storeRepository,
            @Autowired RetailerUserRepository retailerUserRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.storeRepository = storeRepository;
        this.retailerUserRepository = retailerUserRepository;
        this.s3Client = s3Client;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public UUID upload(MultipartFile file, User user) {


        String text = null;
        try {
            String bucketName = "a-item-connect-dev";
            String fileObjKeyName = (file.getName() + "_" + ZonedDateTime.now())
                    .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "");
            fileObjKeyName = "pictures/" + fileObjKeyName;
            ObjectMetadata metadata = new ObjectMetadata();
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName,
                    file.getInputStream(), metadata);
            s3Client.putObject(request);

            PictureModel model = new PictureModel();
            model.setId(UUID.randomUUID().toString());
            model.setOriginalFileName(file.getOriginalFilename());
            model.setPath(fileObjKeyName);
            model = pictureRepository.save(model);
            return UUID.fromString(model.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PictureResponse getPictureDetails(String pictureId, User user) {

        try {
            PictureModel model = pictureRepository.findById(pictureId)
                    .orElseThrow(IllegalArgumentException::new);
            String bucketName = "a-item-connect-dev";
            S3Object fullObject
                    = s3Client.getObject(new GetObjectRequest(bucketName, model.getPath()));


            String base64Image = Base64.getEncoder()
                    .encodeToString(fullObject.getObjectContent().readAllBytes());


            PictureResponse response = new PictureResponse();
            response.setId(pictureId);
            response.setContent(base64Image);
            response.setOriginalFileName(model.getOriginalFileName());
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
