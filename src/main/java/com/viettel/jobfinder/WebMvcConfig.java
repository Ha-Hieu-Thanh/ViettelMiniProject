package com.viettel.jobfinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.viettel.jobfinder.shared.annotation.CurrentUserArgumentResolver;
import com.viettel.jobfinder.shared.interceptor.EmployeePermissionInterceptor;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private CurrentUserArgumentResolver currentUserArgumentResolver;
  @Autowired
  private EmployeePermissionInterceptor employeePermissionInterceptor;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(currentUserArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(employeePermissionInterceptor)
        .addPathPatterns("/user/whoami");
  }

}
