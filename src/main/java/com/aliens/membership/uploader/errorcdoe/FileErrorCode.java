package com.aliens.membership.uploader.errorcdoe;

import com.aliens.membership.global.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * File에 대한 에러는 3000번대 사용.
 */
@AllArgsConstructor
@Getter
public enum FileErrorCode implements ErrorCodeIfs {

    UPLOAD_ERROR(400, 3001,"파일 업로드 실패" ),
    DOWNLOAD_ERROR(400,3002 ,"파일 조회 실패" );

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}