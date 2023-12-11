package com.aliens.membership.auth.controller.dto.validation;

import com.aliens.membership.auth.dto.LoginRequestDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginRequestDtoTest {
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @DisplayName("로그인 아이디 유효성 검사 - 성공 ")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "s%%", "3580w@"})
    void loginIdTest(String input) {
        LoginRequestDto loginRequestDto = new LoginRequestDto(input, "rightPasswordRightPassword");

        assertThrows(RuntimeException.class, () -> {
            validator.validate(loginRequestDto);
        }, "올바르지 않은 형식의 이메일입니다.");
    }

    @DisplayName("로그인 비밀번호 유효성 검사 - 성공 ")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "s%%", "3580w@","12312312"})
    void passwordTest(String input) {
        LoginRequestDto loginRequestDto = new LoginRequestDto("right@example.com",input);

        assertThrows(RuntimeException.class, () -> {
            validator.validate(loginRequestDto);
        }, "올바르지 않은 형식의 비밀번호입니다.");
    }
}
