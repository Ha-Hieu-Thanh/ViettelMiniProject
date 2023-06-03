package com.viettel.jobfinder.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.security.SecurityConstants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // TODO Auto-generated method stub
    super.doFilter(request, response, chain);
  }

  // /login /authenticate
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    System.out.println("attemptAuthentication");
    try {
      User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
      Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
      return authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    System.out.println("successfulAuthentication");
    System.out.println(authResult.getName());
    System.out.println(authResult.getCredentials().toString());
    String access_token = JWT.create()
        .withClaim("username", authResult.getName())
        .withClaim("role", authResult.getAuthorities().toArray()[0].toString())
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION))
        .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(SecurityConstants.ACCESS_SECRET_KEY));
    String refresh_token = JWT.create()
        .withClaim("username", authResult.getName())
        .withClaim("role", authResult.getAuthorities().toArray()[0].toString())
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_TOKEN_EXPIRATION))
        .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(SecurityConstants.REFRESH_SECRET_KEY));
    // Tạo một đối tượng ObjectMapper từ thư viện Jackson
    ObjectMapper objectMapper = new ObjectMapper();

    // Tạo một đối tượng JsonNode chứa mã thông báo truy cập
    ObjectNode responseBody = objectMapper.createObjectNode();
    // Thêm token vào phản hồi
    responseBody.put("access_token", access_token);
    responseBody.put("refresh_token", refresh_token);
    // Thiết lập kiểu nội dung và ghi đối tượng JSON vào phần thân của phản hồi
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(responseBody.toString());
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(failed.getMessage());
    response.getWriter().flush();
  }

}
