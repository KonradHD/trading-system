package com.tradingsystem.backend.adnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tradingsystem.backend.utils.PhoneValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PhoneValidator.class)
public @interface ValidPhoneNumber {
    public String message() default "Invalid phone number.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
