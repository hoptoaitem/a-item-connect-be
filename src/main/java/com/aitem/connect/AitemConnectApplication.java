package com.aitem.connect;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class AitemConnectApplication {

    public static void main(String[] args) {

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
}
