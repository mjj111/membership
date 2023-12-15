package com.aliens.membership.uploader;

import com.aliens.membership.uploader.dto.UploadFileDto;
import com.aliens.membership.uploader.errorcdoe.FileErrorCode;
import com.aliens.membership.global.exception.ApiException;
import com.aliens.membership.global.config.property.LocalUploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LocalUploader {

    private final LocalUploadProperties localUploadProperties;

    public List<String> upload(UploadFileDto uploadFileDto) {
        if (uploadFileDto.files() == null) {
            return Collections.emptyList();
        }

        List<String>  savePathList = new ArrayList<>();

        uploadFileDto.files().forEach(multipartFile -> {
            String originalName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            Path savePath = Paths.get(localUploadProperties.getUploadPath(),
                    uuid + "_" + originalName);
            try{
                multipartFile.transferTo(savePath);// 파일 저장
                savePathList.add(savePath.toFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiException(FileErrorCode.UPLOAD_ERROR, "파일 업로드 실패");
            }
        });
        return savePathList;
    }
}
