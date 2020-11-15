package com.aitem.connect;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class AitemConnectApplication {

    public static void main(String[] args) {
        System.setProperty("aws.accessKeyId", "AKIA6C5C7W2C4TVVHWNV");
        System.setProperty("aws.secretKey", "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");

        SpringApplication.run(AitemConnectApplication.class, args);
    }

    @Bean
    public AmazonS3 amazonS3(SwaggerConfigProperties swaggerConfigProperties) {
        Regions clientRegion = Regions.US_EAST_2;
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(clientRegion)
                .build();
    }

    @Bean
    public FirebaseMessaging getFireBaseApp() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(ResourceUtils.getFile("classpath:test.json"));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://hopto-70c8c.firebaseio.com")
                .build();

        return FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options));
    }
}
