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

public class Test {

	public static void main(String[] args) {
		String str="78655#@@@#=--=---0011";
		str = str.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "");
		System.out.println(str);
	}
}


