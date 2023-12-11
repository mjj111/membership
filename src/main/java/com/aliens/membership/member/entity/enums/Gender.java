package com.aliens.membership.member.entity.enums;


public enum Gender {
    MALE("g1"),
    FEMALE("g2"),
    OTHER("g3");

    private final String message;

    Gender(
            String message
    ) {
        this.message = message;
    }
}