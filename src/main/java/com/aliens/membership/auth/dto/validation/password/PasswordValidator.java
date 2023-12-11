package com.aliens.membership.auth.dto.validation.password;

import com.aliens.membership.auth.controller.AuthErrorCode;
import com.aliens.membership.global.exception.ApiException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && value.length() >= 12) {
            return true;
        }
        throw new ApiException(AuthErrorCode.INVALID_PASSWORD, "올바르지 않은 형식의 비밀번호입니다.");
    }

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
