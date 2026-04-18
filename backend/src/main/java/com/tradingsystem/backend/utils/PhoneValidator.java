package com.tradingsystem.backend.utils;

import com.tradingsystem.backend.adnotation.ValidPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context){
        // TODO:
        return true;
    }
}
