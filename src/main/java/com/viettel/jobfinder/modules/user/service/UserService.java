package com.viettel.jobfinder.modules.user.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.viettel.jobfinder.modules.user.User;
import com.viettel.jobfinder.modules.user.dto.TokenResponse;

public interface UserService {
    User getUser(Long id);

    User getUser(String username);

    User registerUser(User user);

    TokenResponse getToken(User user);
}