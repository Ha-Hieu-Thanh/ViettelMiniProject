package com.viettel.jobfinder.shared.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class EmployeePermissionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    System.out.println("Interceptor");
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();

    // if (authentication != null && authentication.isAuthenticated()) {
    // if
    // (authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList())
    // .contains("EMPLOYEE")) {
    // return true;
    // }
    // ;
    // }
    // response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    // return false;
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    System.out.println("Interceptor afterCompletion");
  }
}
