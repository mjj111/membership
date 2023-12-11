package com.aliens.membership.auth.service;

import com.aliens.membership.auth.controller.AuthErrorCode;
import com.aliens.membership.auth.token.TokenProvider;
import com.aliens.membership.auth.dto.AuthTokenDto;
import com.aliens.membership.auth.dto.LoginRequestDto;
import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.auth.entity.Member;
import com.aliens.membership.auth.entity.RefreshToken;
import com.aliens.membership.auth.repository.MemberRepository;
import com.aliens.membership.auth.repository.RefreshTokenRepository;
import com.aliens.membership.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public AuthTokenDto login(LoginRequestDto loginRequestDto) {
        Member loginMember = getLoginMember(loginRequestDto);
        MemberInfoForToken loginMemberInfo = loginMember.getMemberInfoForToken();

        AuthTokenDto authTokenDto = generateAuthTokenDto(loginMemberInfo);
        refreshTokenRepository.save(RefreshToken.from(authTokenDto.refreshToken(), loginMember));
        return authTokenDto;
    }

    private Member getLoginMember(LoginRequestDto loginRequestDto) {
        return memberRepository.findByLoginIdAndPassword(
                loginRequestDto.loginId(),
                loginRequestDto.password());
    }

    private AuthTokenDto generateAuthTokenDto(MemberInfoForToken memberInfoForToken) {
        String accessToken = tokenProvider.generateAccessToken(memberInfoForToken);
        String refreshToken = tokenProvider.generateRefreshToken(memberInfoForToken);
        return new AuthTokenDto(accessToken,refreshToken);
    }

    @Transactional
    @Override
    public void logout(AuthTokenDto authTokenDto) {
        isNotExpiredRefreshToken(authTokenDto);
        expireRefreshToken(authTokenDto);
    }

    private void isNotExpiredRefreshToken(AuthTokenDto authTokenDto) {
        if (!tokenProvider.isNotExpiredToken(authTokenDto.refreshToken())) {
            expireRefreshToken(authTokenDto);
            throw new ApiException(AuthErrorCode.EXPIRED_REFRESH_TOKEN, "RefreshToken 의 유효기간이 지났습니다.");
        }
    }

    private void expireRefreshToken(AuthTokenDto authTokenDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(authTokenDto.refreshToken());
        refreshToken.expire();
    }

    @Override
    public AuthTokenDto reissue(AuthTokenDto authTokenDto) {
        isExpiredAccessToken(authTokenDto);
        isNotExpiredRefreshToken(authTokenDto);
        isExpiredRefreshTokenInDB(authTokenDto);

        RefreshToken refreshEntity  = refreshTokenRepository.findByRefreshToken(authTokenDto.refreshToken());
        MemberInfoForToken memberInfoForToken = refreshEntity.getMemberInfoForToken();

        String newAccessToken = tokenProvider.generateAccessToken(memberInfoForToken);

        return new AuthTokenDto(newAccessToken, authTokenDto.refreshToken());
    }

    private void isExpiredAccessToken(AuthTokenDto authTokenDto) {
        if (tokenProvider.isNotExpiredToken(authTokenDto.accessToken())) {
            throw new ApiException(AuthErrorCode.NOT_ACCESS_TOKEN_FOR_REISSUE, "AccessToken 의 유효기간 남았습니다.");
        }
    }

    private void isExpiredRefreshTokenInDB(AuthTokenDto authTokenDto) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(authTokenDto.refreshToken());
        if (refreshTokenEntity.isExpired()) {
            throw new ApiException(AuthErrorCode.EXPIRED_REFRESH_TOKEN, "RefreshToken 의 유효기간이 지났습니다.");
        }
    }
}
