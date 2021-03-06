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
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

@EnableAsync
@SpringBootApplication
public class AitemConnectApplication {

    private static final String FIREBASE = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"hopto-70c8c\",\n" +
            "  \"private_key_id\": \"7d705fda36a3432f4d241a454dde6443d5f1e2bd\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgS3g2JP2OOpgZ\\ndQthHcsc00FD0/bOhVw4khtdbEDwgD+Q+DnSQblNFBGLMtlWlGlA/vtdd0VJoPS/\\ntjVjtbn/IB3re8edo3aOd2GUH6xPy65Bql9GqnY4S77s3WKWC9OEJy0PFz9ubzft\\nIMHSPGP+lkmRdbgJEUkzt69J04gkqPIQ2EZ/BaM/Gh8NdZCUc+8OfzHFPogP7J0j\\nF2Fx6Y8HlhPMR0uEcsL7sfHAHfVGVG9fQbg3bvQXMH7XSC/nZm65v+M1O1gmzfrO\\nexk7le5ZXaLwYi3F8NO3zflSlsgrlPinRgUSKzc9X+m/IuvOHcHLVYE08Z41O7SV\\nNThvwumVAgMBAAECggEAAjVapAz+lPelMfqXwXWGihs1/mV8nxadcTvAFpl/y+B0\\ndRPvwKq+TxEhob7oWXtEzPaxB2C14BW7MYCk5n6x3+bfyqdYq3O9qyj6u5lJHOCp\\nQgcty1yHXESyOMi0YO1WSMSSaBoRpLzxsrm9ZImyS1G46eu1hqMfP1AT9pWBvhbe\\niF1mn4ag1+6A8R5QQCxtSJxiYv7IbjvnSCM1WkvbGe7cOJwfaiUdt+bKyiHUIqi9\\nEQN50hmB4/frM3wWIZJpzM+SMVK+/jcsmRun0/JGR2HW3CdDztL8ScIcZyzItinR\\nx/8szIzerRj3CTiPMansPsJ412OL0HvQsg0ZO0pFMQKBgQDR93Q0eCkGZ+NpShCg\\nWYAnLMyjW60fILySuYK7RUM/VJOU9o3c9YRP2YFcRkpQcBqPn9nLQY1HY3UuLw/k\\nqHtHCGG0CyQ3IwAy9qh9r/qPcmR0RlmGQAnBxAXdqoXfZOKUCSJ0cOwLMjBU5tBQ\\nwBOisFHl7NjWqYhW2G3DFeYCsQKBgQDDcCUbhaUB/rzso1q4qbaTNL8aXc0EwB9I\\n2OoceWDyjNDJmX7slJffFLgmQsP/bT08aaat1Lvx46F3VqX8SUc+H1FgmUX8hZZn\\ne4Kr6eW7eeaQ+pEivKhzNA1ue6o17wUCW3m+ZxN9/eomklv8dBlpE91HQXdBBym1\\n/7/jcBJmJQKBgDedUuNe/Ibc16iY3KSMcFxwClguvUFke25lwtH1u6U3ssufLt5b\\nDcvUseYOHDsIJpDqZ1bmKxTArFYqUwxe8CBVgQOee9464B+fo+t+xGBgjWkbBTWB\\nGtR/JoqbU1BjuXw6myy6iFtC1Ph4Fq984PaTu22qeZkKWLmgJBqrXzxRAoGAPgTP\\ncb1mUIzC7SElf+YzTuF1J2vOzIwfIWHcYO7PqgivlNoF4hOBYkRT76hORDcpMIui\\nXhVdEvSWX6V6hOXIGtmbXFknUWwe/3W9UMqnoWecmDM7bVeK6QwCmyXZLMZNw6K9\\nroNNNzUlYdoeIK3DBopMNPl8W4FnZh7jiMweupUCgYEAwom7aSCXXdK3/Xi3huHX\\n36I+OGZqDhRYsyXBQ0prvMutI+88O5WSTjVd88V38QoJGWayxjfTvcxg55pWJ7cR\\ng60rl5w8Oi7KEls1kGXQk+KKm9O8Vq+KKxrWTELiU8Pxt9440nGDRk4ec3M3L5sP\\nZttK5S0vwJ0AlTxr7/UQcd4=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-9pre1@hopto-70c8c.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"101963506388112862480\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-9pre1%40hopto-70c8c.iam.gserviceaccount.com\"\n" +
            "}\n";

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

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(FIREBASE.getBytes())))
                .setDatabaseUrl("https://hopto-70c8c.firebaseio.com")
                .build();

        return FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options));
    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }
}
