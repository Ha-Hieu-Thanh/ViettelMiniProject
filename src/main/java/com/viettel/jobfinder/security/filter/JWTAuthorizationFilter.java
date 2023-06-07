package com.viettel.jobfinder.security.filter;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.service.UserServiceImpl;
import com.viettel.jobfinder.security.SecurityConstants;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

  // Authorization: Bearer <token>
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    System.out.println("JWT Authorization Filter");
    String requestURI = request.getRequestURI();
    if (requestURI.equals("/user/refresh")) {
      // Nếu request là endpoint "/refresh", bỏ qua xác thực token
      filterChain.doFilter(request, response);
      return;
    }

    String header = request.getHeader("Authorization"); // Bearer <token>

    if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.replace(SecurityConstants.BEARER, "");
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.ACCESS_SECRET_KEY))
        .build()
        .verify(token);
    String username = decodedJWT.getClaim("username").asString();
    String role = decodedJWT.getClaim("role").asString();

    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections
        .singletonList(new SimpleGrantedAuthority(role)));
    System.out.println(authentication.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
