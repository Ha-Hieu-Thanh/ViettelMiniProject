package com.viettel.jobfinder.modules.user.controller;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.dto.UserRequestDto;
import com.viettel.jobfinder.modules.user.dto.UserResponseDto;
import com.viettel.jobfinder.modules.user.service.UserService;
import com.viettel.jobfinder.security.SecurityConstants;
import com.viettel.jobfinder.shared.annotation.CurrentUser;
import com.viettel.jobfinder.shared.annotation.Public;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<String> findById(@PathVariable Long id) {

		return new ResponseEntity<>(userService.getUser(id).getUsername(), HttpStatus.OK);
	}

	@PreAuthorize("permitAll()")
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequestDto userDto) {
		// convert dto to entity
		User user = userDto.toUser();
		userService.registerUser(user);
		ObjectNode responseBody = userService.getToken(user);
		return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
	}

	@PostMapping("/refresh")
	@Public
	public ResponseEntity<Object> refresh(@RequestHeader("Authorization") String token) {
		// token = Barear + token
		System.out.println("CHECK REFRESH");
		if (token != null && token.startsWith("Bearer ")) {
			String refresh_token = token.substring(7);
			DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstants.REFRESH_SECRET_KEY))
					.build()
					.verify(refresh_token);
			String username = decodedJWT.getClaim("username").asString();
			User user = userService.getUser(username);
			ObjectNode responseBody = userService.getToken(user);
			return new ResponseEntity<>(responseBody, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/whoami")
	public ResponseEntity<UserResponseDto> whoami(@CurrentUser User user) {
		System.out.println("whoami");
		if (user != null) {
			return new ResponseEntity<>(UserResponseDto.fromUser(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
