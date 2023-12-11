package com.aliens.membership.auth.token;

import com.aliens.membership.auth.dto.MemberInfoForToken;

public interface TokenProvider {
    String generateAccessToken(MemberInfoForToken memberInfoForToken);

    String generateRefreshToken(MemberInfoForToken memberInfoForToken);

    boolean isNotExpiredToken(String token);

    MemberInfoForToken getLoginMemberInfoForTokenByAccessToken(String accessToken);
}
