package com.viettel.jobfinder.shared.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.modules.user.service.UserServiceImpl;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

  @Autowired
  private final UserService userService;

  @Autowired
  public CurrentUserArgumentResolver(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(CurrentUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    CurrentUser annotation = parameter.getParameterAnnotation(CurrentUser.class);
    String attributeName = annotation.value();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = authentication != null
        ? userService.getUser(authentication.getPrincipal().toString())
        : null;
    System.out.println("Annotation" + user.getId());
    if ("id".equals(attributeName)) {
      // If value is specified, return the specific attribute from User object
      return user != null ? user.getId() : null;
    }
    return user;
  }
}
