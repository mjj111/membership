package com.aliens.membership.auth.controller;

import com.aliens.membership.auth.dto.AuthTokenDto;
import com.aliens.membership.auth.dto.LoginRequestDto;
import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.auth.service.AuthService;
import com.aliens.membership.global.api.Api;
import com.aliens.membership.global.custom.jwt.LoginMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping()
    public Api<AuthTokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return Api.OK(authService.login(loginRequestDto));
    }

    @GetMapping()
    public Api<MemberInfoForToken> getMemberInfo(@LoginMember MemberInfoForToken memberInfoForToken) {
        return Api.OK(memberInfoForToken);
    }

    @PostMapping("/logout")
    public Api<String> logout(@RequestBody AuthTokenDto authTokenDto) {
        authService.logout(authTokenDto);
        return Api.OK("로그아웃 되었습니다.");
    }
}