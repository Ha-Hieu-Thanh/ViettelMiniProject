package com.viettel.jobfinder.shared.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {

  String message() default "Invalid gender";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
