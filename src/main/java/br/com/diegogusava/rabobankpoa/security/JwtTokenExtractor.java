package br.com.diegogusava.rabobankpoa.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtTokenExtractor {

    private JwtTokenExtractor() {
    }

    public static String getOperatorId() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        return (String) authentication.getDetails();
    }

}