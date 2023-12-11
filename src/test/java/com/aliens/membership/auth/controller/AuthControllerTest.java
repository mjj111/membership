package com.aliens.membership.auth.controller;

import com.aliens.membership.auth.dto.AuthTokenDto;
import com.aliens.membership.auth.dto.LoginRequestDto;
import com.aliens.membership.auth.entity.Member;
import com.aliens.membership.auth.repository.MemberRepository;
import com.aliens.membership.auth.repository.RefreshTokenRepository;
import com.aliens.membership.auth.service.AuthService;
import com.aliens.membership.global.config.JwtProperties;
import com.aliens.membership.member.entity.enums.Gender;
import com.aliens.membership.member.entity.enums.Role;
import com.aliens.membership.member.entity.MemberInfo;
import com.aliens.membership.member.repository.MemberInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

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

    @Autowired
    ObjectMapper mapper;

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
        givenPassword = "passwordpasswordpassword";
        givenMemberInfo = MemberInfo.of("김명준", Role.ADMIN, Gender.MALE);
        memberInfoRepository.save(givenMemberInfo);
        giveMember = memberRepository.save(new Member(givenLoginId,givenPassword,givenMemberInfo));
    }

    @Test
    @DisplayName("컨트롤러에서 로그인 시도 - 성공")
    void loginTest() throws Exception{
        String inputBody = mapper.writeValueAsString(new LoginRequestDto(givenLoginId, givenPassword));
        mvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("컨트롤러에서 없는 회원으로 로그인 시도 - 실패")
    void loginFailTest() throws Exception {
        String inputBody = mapper.writeValueAsString(new LoginRequestDto("notnot@naver.com", givenPassword));
        mvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputBody))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("컨트롤러에서 ArgumentResolver 로 jwt 헤더에서 회원정보 파싱 - 성공")
    void memberInfoTest() throws Exception {
        String accessToken = authService.login(new LoginRequestDto(givenLoginId, givenPassword)).accessToken();
        String content = mvc.perform(get("/auth").header("Authorization", accessToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertNotNull(content);
    }

    @Test
    @DisplayName("컨트롤러에서 AuthDto 를 받아 로그아웃 실행 - 성공")
    void logoutTest() throws Exception {
        AuthTokenDto authTokenDto = authService.login(new LoginRequestDto(givenLoginId, givenPassword));
        String inputBody = mapper.writeValueAsString(authTokenDto);

        mvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputBody))
                .andExpect(status().isOk());
    }
}