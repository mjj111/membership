package com.aliens.membership.auth.service;

import com.aliens.membership.auth.dto.AuthTokenDto;
import com.aliens.membership.auth.dto.LoginRequestDto;
import com.aliens.membership.auth.entity.Member;
import com.aliens.membership.auth.entity.RefreshToken;
import com.aliens.membership.auth.repository.MemberRepository;
import com.aliens.membership.auth.repository.RefreshTokenRepository;
import com.aliens.membership.global.config.JwtProperties;
import com.aliens.membership.member.entity.enums.Gender;
import com.aliens.membership.member.entity.enums.Role;
import com.aliens.membership.member.entity.MemberInfo;
import com.aliens.membership.member.repository.MemberInfoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberInfoRepository memberInfoRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    JwtProperties jwtProperties;

    String givenLoginId;
    String givenPassword;
    MemberInfo givenMemberInfo;
    Member giveMember;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAll();
        memberRepository.deleteAll();
        memberInfoRepository.deleteAll();

        givenLoginId = "tmp@example.com";
        givenPassword = "password";
        givenMemberInfo = MemberInfo.of("email","김명준", Role.ADMIN, Gender.MALE);
        memberInfoRepository.save(givenMemberInfo);
        giveMember = memberRepository.save(new Member(givenLoginId,givenPassword,givenMemberInfo));
    }

    @Test
    @DisplayName("로그인 요청에 따른 토큰발급")
    void loginTest() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(givenLoginId,givenPassword);
        AuthTokenDto authTokenDto = authService.login(loginRequestDto);

        Assertions.assertNotNull(authTokenDto);
    }

    @Test
    @DisplayName("로그아웃 요청에 따른 리프레쉬토큰 상태 변경확인")
    void logoutTest() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(givenLoginId,givenPassword);
        AuthTokenDto authTokenDto = authService.login(loginRequestDto);

        authService.logout(authTokenDto);

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(authTokenDto.refreshToken());
        Assertions.assertTrue(refreshTokenEntity.isExpired());
    }

    @Test
    @DisplayName("accessToken 만료로 인한 재발급")
    void reissueTest() throws InterruptedException {
        long tmpProperty = jwtProperties.getAccessTokenValidTime();
        jwtProperties.setAccessTokenValidTime(1); //AccessToken 유효기한 짧게변경
        LoginRequestDto loginRequestDto = new LoginRequestDto(givenLoginId,givenPassword);
        AuthTokenDto expiredAuthTokenDto = authService.login(loginRequestDto);

        Thread.sleep(1000); //AccessToken 유효기한 길게 기다려, 토큰 만료
        AuthTokenDto reissuedTokenDto = authService.reissue(expiredAuthTokenDto);
        jwtProperties.setAccessTokenValidTime(tmpProperty); // 설정값 회복

        Assertions.assertNotEquals(expiredAuthTokenDto.accessToken(), reissuedTokenDto.accessToken());
    }
}
