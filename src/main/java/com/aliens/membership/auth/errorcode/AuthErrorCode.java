package com.aliens.membership.auth.errorcode;

import com.aliens.membership.global.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Auth에 대한 에러는 2000번대 사용.
 */
@AllArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCodeIfs {

    EXPIRED_TOKEN(400 , 2001 , "만료된 토큰"),
    INVALID_TOKEN(400,2002 , "올바르지 않은 토큰"),
    NOT_ACCESS_TOKEN_FOR_REISSUE(400, 2003,"재발급하기에는 유효기간이 남은 엑세스토큰"),
    EXPIRED_REFRESH_TOKEN(400, 2004, "리프레시토큰의 유효기간이 지남"),
    INVALID_PASSWORD(400,2005,"올바르지 않은 형식의 비밀번호" ),
    INVALID_EMAIL(400,2006 ,"올바르지 않은 형식의 이메일" ),
    NULL_MEMBER(400,2007,"해당 Member 엔티티 조회 불가" ),
    NULL_REFRESH_TOKEN(400, 2008,"해당 RefreshToken 엔티티 조회불가" );

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
