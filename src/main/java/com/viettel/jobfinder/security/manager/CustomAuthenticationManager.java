// package com.viettel.jobfinder.security.manager;

// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// import com.viettel.jobfinder.modules.user.User;
// import com.viettel.jobfinder.modules.user.service.UserServiceImpl;

// import lombok.AllArgsConstructor;

// @Component
// @AllArgsConstructor
// public class CustomAuthenticationManager implements AuthenticationManager {

// private UserServiceImpl userServiceIml;
// private BCryptPasswordEncoder bCryptPasswordEncoder;

// @Override
// public Authentication authenticate(Authentication authentication) throws
// AuthenticationException {
// System.out.println("Authenticate function");
// User user = null;
// try {
// user = userServiceIml.getUser(authentication.getName());
// } catch (Exception e) {
// throw new BadCredentialsException("User not found");
// }
// System.out.println(authentication.getCredentials().toString());
// System.out.println(user.getPassword());
// if
// (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(),
// user.getPassword())) {
// throw new BadCredentialsException("Wrong password");
// }
// List<SimpleGrantedAuthority> authorities = Collections
// .singletonList(new SimpleGrantedAuthority(user.getRole().getValue()));
// return new UsernamePasswordAuthenticationToken(authentication.getName(),
// authentication.getCredentials(),
// authorities);
// }

// }
