package com.aliens.membership.global.config;

import com.aliens.membership.global.config.property.S3UploadProperties;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AWSConfig {

    private final S3UploadProperties s3UploadProperties;

    //AmazonS3Client를 얻기위한 bean 주입
    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(s3UploadProperties.getAccessKey(), s3UploadProperties.getSecretKey());
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(s3UploadProperties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}