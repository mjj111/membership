package com.aliens.membership.auth.service;

import com.aliens.membership.auth.dto.AuthTokenDto;
import com.aliens.membership.auth.dto.LoginRequestDto;

public interface AuthService {
    AuthTokenDto login(LoginRequestDto loginRequestDto);
    void logout(AuthTokenDto authTokenDto);
    AuthTokenDto reissue(AuthTokenDto authTokenDto);
}
