package com.viettel.jobfinder.shared.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WebsiteLinkValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidWebsiteLink {

    String message() default "Invalid website link";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
