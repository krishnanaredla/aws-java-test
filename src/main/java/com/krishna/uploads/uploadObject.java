package com.krishna.uploads;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;
import java.io.IOException;

public class uploadObject {

    public static void main(String[] args) throws IOException {
        Regions clientRegion = Regions.US_WEST_1;
        String bucketName = "tmp";
        String stringObjKeyName = "test.txt";
        String fileObjKeyName = "single.tar";
        String fileName = "C://models//checkpoint_130000.pth.tar";

        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            //AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            //        .withRegion(clientRegion)
            //        .build();
            AmazonS3ClientBuilder.EndpointConfiguration endpoint = new AmazonS3ClientBuilder.EndpointConfiguration(
                    "http://127.0.0.1:4566", "us_east_1");
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(endpoint)
                    .build();

            // Upload a text string as a new object.
            //s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            System.out.println("Started uploading");
            System.out.println(java.time.LocalTime.now());
            PutObjectRequest request = new PutObjectRequest(bucketName,
                    fileObjKeyName, new File(fileName));
            //ObjectMetadata metadata = new ObjectMetadata();
            //metadata.setContentType("plain/text");
            //metadata.addUserMetadata("title", "someTitle");
            //request.setMetadata(metadata);
            PutObjectResult result = s3Client.putObject(request);
            System.out.println(result);
            System.out.println(java.time.LocalTime.now());
            System.out.println("Completed uploading");
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
