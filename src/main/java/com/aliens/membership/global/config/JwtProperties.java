package com.aliens.membership.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Component
public class JwtProperties {
    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.access_token_valid_time}")
    private long accessTokenValidTime;

    @Value("${jwt.refresh_token_valid_time}")
    private long refreshTokenValidTime ;

    public byte[] getBytesSecretKey() {
        return secretKey.getBytes(StandardCharsets.UTF_8);
    }

    public long getRefreshTokenValidTime() {
        return refreshTokenValidTime;
    }

    public long getAccessTokenValidTime() {
        return accessTokenValidTime;
    }
}
