package com.viettel.jobfinder.shared.annotation;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('EMPLOYEE')")
public @interface EmployeePermission {
}