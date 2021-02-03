package com.krishna.downloads;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;

import java.io.File;
import java.io.IOException;

public class singleDownload {

    public static void main(String[] args) throws IOException {
        String bucketName = "tmp";
        String keyName = "transferupload.tar";
        String filePath = "C://work///java//SingleDownload.tar";
        try {
        AmazonS3ClientBuilder.EndpointConfiguration endpoint = new AmazonS3ClientBuilder.EndpointConfiguration(
                "http://127.0.0.1:4566", "us_east_1");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpoint)
                .build();

        GetObjectRequest request = new GetObjectRequest(bucketName,
                keyName);


            System.out.println("download start.");
            System.out.println(java.time.LocalTime.now());
            s3Client.getObject(request, new File(filePath));
            System.out.println(java.time.LocalTime.now());
            System.out.println("download complete.");
        }
     catch (AmazonServiceException e) {
        e.printStackTrace();
    } catch (SdkClientException e) {
        e.printStackTrace();
    }


    }
}
