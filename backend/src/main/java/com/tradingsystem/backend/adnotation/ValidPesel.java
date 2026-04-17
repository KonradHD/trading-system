package com.tradingsystem.backend.adnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tradingsystem.backend.utils.PeselValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PeselValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPesel {
    String message() default "Invalid PESEL number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
