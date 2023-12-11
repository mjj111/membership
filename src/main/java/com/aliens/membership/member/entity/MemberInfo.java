package com.aliens.membership.member.entity;

import com.aliens.membership.global.entity.BaseEntity;
import com.aliens.membership.auth.dto.MemberInfoForToken;
import com.aliens.membership.member.entity.enums.Gender;
import com.aliens.membership.member.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Entity
public class MemberInfo extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;


    public static MemberInfo of(String name, Role role, Gender gender) {
        return new MemberInfo(name, role, gender);
    }

    public MemberInfoForToken getInfoForToken() {
        return new MemberInfoForToken(super.getId(), name, role.getMessage());
    }
}