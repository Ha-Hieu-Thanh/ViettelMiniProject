package com.viettel.jobfinder.modules.user.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viettel.jobfinder.modules.user.dto.LoginDto;
import com.viettel.jobfinder.modules.user.dto.TokenResponse;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.dto.UserRequestDto;
import com.viettel.jobfinder.modules.user.dto.UserResponseDto;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.security.SecurityConstants;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.Public;
import com.viettel.jobfinder.shared.exception.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
@Tag(name = "Authentication")
@Order(1)
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/{id}")
	@Public
	public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {

		return new ResponseEntity<>(UserResponseDto.fromUser(userService.getUser(id)), HttpStatus.OK);
	}

	@PostMapping("/register")
	@Public
	public ResponseEntity<TokenResponse> createUser(@Valid @RequestBody UserRequestDto userDto) {
		// convert dto to entity
		User user = userDto.toUser();
		userService.registerUser(user);
		TokenResponse responseBody = userService.getToken(user);
		return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
	}

	@PostMapping("/refresh")
	@Public
	public ResponseEntity<TokenResponse> refresh(@RequestHeader("Authorization") String token) {
		// token = Barear + token
		System.out.println("CHECK REFRESH");
		if (token != null && token.startsWith("Bearer ")) {
			String refresh_token = token.substring(7);
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.REFRESH_SECRET_KEY))
					.build()
					.verify(refresh_token);
			String username = decodedJWT.getClaim("username").asString();
			User user = userService.getUser(username);
			TokenResponse responseBody = userService.getToken(user);
			return new ResponseEntity<>(responseBody, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/whoami")
	public ResponseEntity<UserResponseDto> whoami(@CurrentUser User user) {
		if (user != null) {
			return new ResponseEntity<>(UserResponseDto.fromUser(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/login")
	@Public
	@ApiResponse(responseCode = "200", description = "Successful login", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)))
	@ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto login) {
		try {
			User user = userService.getUser(login.getUsername());
			if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(Arrays.asList("Wrong password")));
			}
			TokenResponse responseBody = userService.getToken(user);
			return new ResponseEntity<>(responseBody, HttpStatus.OK);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(Arrays.asList(e.getMessage())));
		}
	}
}
