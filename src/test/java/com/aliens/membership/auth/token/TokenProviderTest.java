package com.aliens.membership.auth.token;

import com.aliens.membership.auth.dto.AuthTokenDto;
import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.member.entity.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    MemberInfoForToken givenMemberInfoForToken;
    String givenMemberName = "김명준";

    @BeforeEach
    void setUp() {
        givenMemberName = "김명준";
        givenMemberInfoForToken = new MemberInfoForToken(1L,givenMemberName, Role.MEMBER.getMessage());
    }

    @Test
    @DisplayName("엑세스토큰으로부터 로그인멤버 정보 추출")
    void getLoginMemberTokenInfoByAccessTokenTest() {
        String accessToken = tokenProvider.generateAccessToken(givenMemberInfoForToken);
        MemberInfoForToken memberTokenInfoFromForToken = tokenProvider.getLoginMemberInfoForTokenByAccessToken(accessToken);

        Assertions.assertEquals(memberTokenInfoFromForToken, givenMemberInfoForToken);
    }

    @Test
    @DisplayName("엑세스토큰 발행, 디코딩하여 입력값과 비교")
    void generateAccessToken() {
        String accessToken = tokenProvider.generateAccessToken(givenMemberInfoForToken);

        AuthTokenDto authTokenDto = new AuthTokenDto(accessToken,"RefreshToken is not need for this test");
        String parsedMemberName = tokenProvider.getLoginMemberInfoForTokenByAccessToken(accessToken).name();
        Assertions.assertEquals(parsedMemberName, givenMemberName);
    }

    @Test
    @DisplayName("리프레쉬토큰 발행, 디코딩하여 입력값과 비교")
    void generateRefreshTokenTest() {
        String refreshToken = tokenProvider.generateRefreshToken(givenMemberInfoForToken);

        AuthTokenDto authTokenDto = new AuthTokenDto(refreshToken,"RefreshToken is not need for this test");
        String parsedMemberName = tokenProvider.getLoginMemberInfoForTokenByAccessToken(authTokenDto.accessToken()).name();
        Assertions.assertEquals(parsedMemberName, givenMemberName);
    }

    @Test
    @DisplayName("토큰 유효기간 검사")
    void isExpiredTokenTest() {
        String accessToken = tokenProvider.generateAccessToken(givenMemberInfoForToken);
        Assertions.assertTrue(tokenProvider.isNotExpiredToken(accessToken));
    }
}
