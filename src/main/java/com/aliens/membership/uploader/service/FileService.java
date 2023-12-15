package com.aliens.membership.uploader.service;

import com.aliens.membership.uploader.dto.UploadFileDto;

import java.util.List;

public interface FileService {
    List<String> upload(UploadFileDto uploadFileDto);
    void delete(List<String> fileNames);
}
