package com.aliens.membership.auth.dto;

import com.aliens.membership.auth.dto.validation.email.Email;
import com.aliens.membership.auth.dto.validation.password.Password;

public record LoginRequestDto(@Email String loginId, @Password String password) {
}

