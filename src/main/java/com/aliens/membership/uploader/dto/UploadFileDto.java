package com.aliens.membership.uploader.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadFileDto(List<MultipartFile> files) {
}
