package com.viettel.jobfinder.shared.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.net.URL;

public class WebsiteLinkValidator implements ConstraintValidator<ValidWebsiteLink, String> {

    @Override
    public void initialize(ValidWebsiteLink constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        try {
            // Create a URL object from the provided string
            URL websiteUrl = new URL(value);

            // Check if the URL's protocol is HTTP or HTTPS
            return websiteUrl.getProtocol().equalsIgnoreCase("http")
                    || websiteUrl.getProtocol().equalsIgnoreCase("https");
        } catch (MalformedURLException e) {
            return false; // Malformed URL
        }
    }
}
