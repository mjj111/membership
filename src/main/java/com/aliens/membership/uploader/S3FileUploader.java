package com.aliens.membership.uploader;

import com.aliens.membership.global.config.property.S3UploadProperties;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class S3FileUploader {

    private final S3UploadProperties s3UploadProperties;
    private final AmazonS3Client amazonS3Client;

    public String upload(String filePath) {
        File targetFile = new File(filePath);
        String uploadImageUrl = putS3(targetFile, targetFile.getName());
        targetFile.deleteOnExit();

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(s3UploadProperties.getBucket(),
                fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(s3UploadProperties.getBucket(),fileName).toString();
    }

    public void removeS3File (String fileName) {
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(s3UploadProperties.getBucket(), fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
