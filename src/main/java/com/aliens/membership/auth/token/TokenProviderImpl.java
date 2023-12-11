package com.aliens.membership.auth.token;

import com.aliens.membership.auth.controller.AuthErrorCode;
import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.global.config.JwtProperties;
import com.aliens.membership.global.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProviderImpl implements TokenProvider {

    private final JwtProperties properties;

    @Override
    public String generateRefreshToken(MemberInfoForToken memberInfoForToken) {
        Claims claims = getClaimsFrom(memberInfoForToken);
        return getTokenFrom(claims, properties.getRefreshTokenValidTime());
    }

    private Claims getClaimsFrom(MemberInfoForToken memberInfoForToken) {
        Claims claims = Jwts.claims();
        claims.put("memberInfoId", String.valueOf(memberInfoForToken.id()));
        claims.put("name", memberInfoForToken.name());
        claims.put("role", memberInfoForToken.role());
        return claims;
    }

    private String getTokenFrom(Claims claims, long validTime) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(Keys.hmacShaKeyFor(
                                properties.getBytesSecretKey()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    @Override
    public String generateAccessToken(MemberInfoForToken memberInfoForToken) {
        Claims claims = getClaimsFrom(memberInfoForToken);

        return getTokenFrom(claims, properties.getAccessTokenValidTime());
    }

    private String removeBearer(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    @Override
    public boolean isNotExpiredToken(String token) {
        try {
            return isNotExpired(token);
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    private boolean isNotExpired(String token) {
        return !Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(properties.getBytesSecretKey()))
                .build()
                .parseClaimsJws(removeBearer(token))
                .getBody()
                .getExpiration().before(new Date());
    }

    @Override
    public MemberInfoForToken getLoginMemberInfoForTokenByAccessToken(String accessToken) {
        try {
            return getInfoFrom(accessToken);
        } catch (ExpiredJwtException expired) {
            throw new ApiException(AuthErrorCode.EXPIRED_TOKEN, "토큰이 만료되었습니다.");
        } catch (Exception e) {
            throw new ApiException(AuthErrorCode.INVALID_TOKEN, "올바르지 않은 토큰입니다.");
        }
    }

    public MemberInfoForToken getInfoFrom(String accessToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(properties.getBytesSecretKey()))
                .build()
                .parseClaimsJws(removeBearer(accessToken))
                .getBody();

        return new MemberInfoForToken(
                Long.parseLong(String.valueOf(claims.get("memberInfoId"))),
                String.valueOf(claims.get("name")),
                String.valueOf(claims.get("role"))
        );
    }
}
