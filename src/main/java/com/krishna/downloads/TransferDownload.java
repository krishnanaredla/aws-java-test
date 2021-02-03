package com.krishna.downloads;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.AmazonClientException;
import java.lang.InterruptedException;


import java.util.concurrent.Executors;
import java.io.File;
import java.io.IOException;

public class TransferDownload {
    public static void main(String[] args) throws IOException {
        try {
            String bucketName = "tmp";
            String keyName = "transferupload.tar";
            String filePath = "C://work///java//transferupload.tar";

            AmazonS3ClientBuilder.EndpointConfiguration endpoint = new AmazonS3ClientBuilder.EndpointConfiguration(
                    "http://127.0.0.1:4566", "us_east_1");
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(endpoint)
                    .build();

            int maxUploadThreads = 5;

            TransferManager tm = TransferManagerBuilder
                    .standard()
                    .withS3Client(s3Client)
                    .withMultipartUploadThreshold((long) (5 * 1024 * 1024))
                    .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                    .build();

            ProgressListener progressListener =
                    progressEvent -> System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());

            GetObjectRequest request = new GetObjectRequest(bucketName,
                    keyName);

            request.setGeneralProgressListener(progressListener);
            System.out.println("download Start.");
            System.out.println(java.time.LocalTime.now());

            Download  download = tm.download(request,
                    new File(filePath));

            try {
                download.waitForCompletion();
                System.out.println(java.time.LocalTime.now());
                System.out.println("download complete.");
            } catch (AmazonClientException e) {
                System.out.println("Error occurred while uploading file");
                e.printStackTrace();
            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                tm.shutdownNow();
            }





        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
