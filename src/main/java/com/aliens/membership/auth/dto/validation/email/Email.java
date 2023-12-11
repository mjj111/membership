package com.aliens.membership.auth.dto.validation.email;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
@Documented
public @interface Email {
    String message() default "Invalid Email";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
