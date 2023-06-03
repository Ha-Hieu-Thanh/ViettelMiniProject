package com.viettel.jobfinder.security;

public class SecurityConstants {
    public static final String ACCESS_SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)";
    public static final String REFRESH_SECRET_KEY = "dGkPn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)H@McQfTjWnZr4u7x!A%D*G-Ka";
    public static final int ACCESS_TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    public static final int REFRESH_TOKEN_EXPIRATION = 604800000; // 604800000 milliseconds = 604800 seconds = 7 days.
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/*/register"; // Public path that clients can use to register.
}
