package com.aliens.membership.uploader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S3FileUploadTest {

    @Autowired
    private S3FileUploader s3FileUploader;

    @Test
    void uploadTest() {
        String filePath = "원하는 이미지 주소";
        String uploadName = s3FileUploader.upload(filePath);
        System.out.println(uploadName);
    }

    @Test
    void deleteTest() {
        s3FileUploader.removeS3File("저장했던 파일.jpeg");
    }
}
