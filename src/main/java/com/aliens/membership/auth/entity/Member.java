package com.aliens.membership.auth.entity;

import com.aliens.membership.global.entity.BaseEntity;
import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.member.entity.MemberInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_info_id", referencedColumnName = "id", nullable = false)
    private MemberInfo memberInfo;

    @Override
    public String toString() {
        return String.format("id:  %s, password : %s", this.loginId, this.password);
    }

    public MemberInfoForToken getMemberInfoForToken() {
        return memberInfo.getInfoForToken();
    }

}
