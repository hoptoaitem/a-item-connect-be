package com.aitem.connect.model;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AWSTest {

	public static void main(String[] args) throws IOException {
		Regions clientRegion = Regions.US_EAST_2;
		String bucketName = "a-item-connect-dev";
		//String stringObjKeyName = "test";
		String fileObjKeyName = "test/test/test_"+UUID.randomUUID().toString();
		String fileName = "test";

		try {

			System.out.println(fileObjKeyName);

			System.setProperty("aws.accessKeyId", "AKIA6C5C7W2C4TVVHWNV");
			System.setProperty("aws.secretKey", "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");



			//Java system properties–aws.accessKeyId and aws.secretKey.

			//This code expects that you have AWS credentials set up per:
			// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
			AmazonS3 s3Client = AmazonS3ClientBuilder
					.standard()
					.withRegion(clientRegion)
					.build();

			// Upload a text string as a new object.
			//s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

			// Upload a file as a new object with ContentType and title specified.
			PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File("/Users/achyutneupane/Desktop/junk.txt"));
			ObjectMetadata metadata = new ObjectMetadata();
			//metadata.setContentType("plain/text");
			metadata.addUserMetadata("title", "someTitle");
			request.setMetadata(metadata);
			s3Client.putObject(request);
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
	}
}


