package com.aliens.membership.member.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("admin"),
    MEMBER("member");

    private final String message;

    Role(
            String message
    ) {
        this.message = message;
    }
}