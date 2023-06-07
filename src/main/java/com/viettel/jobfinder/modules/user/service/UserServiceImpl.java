package com.viettel.jobfinder.modules.user.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employee.repository.EmployeeRepository;
import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.employer.repository.EmployerRepository;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.dto.TokenResponse;
import com.viettel.jobfinder.modules.user.repository.UserRepository;
import com.viettel.jobfinder.security.SecurityConstants;
import com.viettel.jobfinder.shared.exception.EntityNotFoundException;
import com.viettel.jobfinder.shared.exception.ForbiddenException;
import com.viettel.jobfinder.shared.exception.NotFoundException;
import com.viettel.jobfinder.shared.exception.UserExistedException;
import com.viettel.jobfinder.shared.sendGrid.SendGridMailService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SendGridMailService sendGridMailService;

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {
        System.out.println("Check getUser(string username)");
        if (username == "anonymousUser") {
            throw new ForbiddenException("You are not allowed to access this resource");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent())
            return user.get();
        else
            throw new NotFoundException("User with " + "username: " + username + " not found");
    }

    @Override
    public User registerUser(User user) {
        // find if user exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            // if user exists, throw exception to announce that user already exists
            throw new UserExistedException(user.getUsername());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        if (user.getRole().getValue().equals("EMPLOYEE")) {
            // handle reg for employee
            employeeRepository.save(new Employee(user));
        } else if (user.getRole().getValue().equals("EMPLOYER")) {
            // handle reg for employer
            employerRepository.save(new Employer(user));
        }

        sendGridMailService.sendWelcomeMail(user.getEmail());
        return user;
    }

    @Override
    public TokenResponse getToken(User user) {
        String new_access_token = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().getValue())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(SecurityConstants.ACCESS_SECRET_KEY));
        String new_refresh_token = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().getValue())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.REFRESH_TOKEN_EXPIRATION))
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC512(SecurityConstants.REFRESH_SECRET_KEY));

        TokenResponse responseBody = new TokenResponse(new_access_token, new_refresh_token);
        return responseBody;
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new NotFoundException("User with id " + id + " not found");
    }

}
