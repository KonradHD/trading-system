package com.tradingsystem.backend.utils;

import com.tradingsystem.backend.adnotation.ValidPesel;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeselValidator implements ConstraintValidator<ValidPesel, String>{

    private static final int[] WEIGHTS = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};
    
    @Override
    public boolean isValid(String pesel, ConstraintValidatorContext context){
        
        // w takim przypadku błąd wyrzuci adnotacja @NotNull
        if(pesel == null || pesel.isBlank()){
            return true; 
        }
        
        if(!pesel.matches("\\d{11}")){
            return false;
        }

        int sum = 0;
        for(int i = 0; i < 10; i++){
            sum += Character.getNumericValue(pesel.charAt(i)) * WEIGHTS[i];
        }

        int controlNumber = Character.getNumericValue(pesel.charAt(10));
        int realControlNumber = (10 - (sum % 10)) % 10;

        return realControlNumber == controlNumber;
    }
}
