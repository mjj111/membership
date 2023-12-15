package com.aliens.membership.uploader.service;

import com.aliens.membership.uploader.LocalUploader;
import com.aliens.membership.uploader.S3FileUploader;
import com.aliens.membership.uploader.dto.UploadFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final LocalUploader localUploader;
    private final S3FileUploader s3FileUploader;

    public List<String> upload(UploadFileDto uploadFileDto) {
        if (uploadFileDto.files() == null) {
            return Collections.emptyList();
        }

        List<String> uploadFilePaths = new ArrayList<>(localUploader.upload(uploadFileDto));

        return uploadFilePaths.stream().map(s3FileUploader::upload).toList();
    }

    public void delete(List<String> fileNames) {
        fileNames.forEach(s3FileUploader::removeS3File);
    }
}
