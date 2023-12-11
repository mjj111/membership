package com.aliens.membership.auth.entity;

import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class RefreshToken extends BaseEntity {

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private boolean expired;

    @OneToOne
    @JoinColumn(nullable = false)
    private Member member;

    public static RefreshToken from(String refreshToken, Member member) {
        return new RefreshToken(refreshToken,false, member);
    }

    public boolean isExpired() {
        return expired;
    }

    public void expire() {
        this.expired = true;
    }

    public MemberInfoForToken getMemberInfoForToken() {
        return member.getMemberInfoForToken();
    }
}
