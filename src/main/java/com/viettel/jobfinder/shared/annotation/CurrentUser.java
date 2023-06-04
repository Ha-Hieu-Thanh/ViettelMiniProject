package com.viettel.jobfinder.shared.annotation;

import java.lang.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
  String value() default "";
}
