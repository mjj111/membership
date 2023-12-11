package com.aliens.membership.auth.dto.validation.email;

import com.aliens.membership.auth.controller.AuthErrorCode;
import com.aliens.membership.global.exception.ApiException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(value);

        if ( matcher.matches()) {
            return true;
        }
        throw new ApiException(AuthErrorCode.INVALID_EMAIL, "올바르지 않은 형식의 이메일입니다.");
    }

    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
