package com.viettel.jobfinder.shared.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

  @Override
  public void initialize(ValidGender constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true; // Null values are considered valid
    }

    // Check if the value is either "male" or "female" (case-insensitive)
    return value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female");
  }
}
