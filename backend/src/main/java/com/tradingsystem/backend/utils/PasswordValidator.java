package com.tradingsystem.backend.utils;

import com.tradingsystem.backend.adnotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String>{

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context){
        // TODO:
        return true;
    }
}
