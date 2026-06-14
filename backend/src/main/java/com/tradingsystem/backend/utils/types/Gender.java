package com.tradingsystem.backend.utils.types;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    Male,
    Female,
    Other;

    @JsonCreator
    public static Gender fromString(String value) {
        if (value == null) {
            return null;
        }
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
