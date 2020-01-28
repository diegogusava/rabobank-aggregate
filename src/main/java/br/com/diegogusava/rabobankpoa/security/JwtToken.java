package br.com.diegogusava.rabobankpoa.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.ArrayList;
import java.util.List;

import static br.com.diegogusava.rabobankpoa.security.SecurityConstants.TOKEN_OPERATOR_ID;

public class JwtToken {

    private JwtBuilder builder;
    private List<String> roles = new ArrayList<>();

    private JwtToken() {
        builder = Jwts.builder();
        var signingKey = SecurityConstants.JWT_SECRET.getBytes();
        builder.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512);
        builder.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE);
        builder.setIssuer(SecurityConstants.TOKEN_ISSUER);
        builder.setAudience(SecurityConstants.TOKEN_AUDIENCE);
    }

    public static JwtToken builder() {
        return new JwtToken();
    }

    public JwtToken withUsername(final String userId) {
        builder.setSubject(userId);
        return this;
    }

    public JwtToken withOperatorId(final String operatorId) {
        return withClaim(TOKEN_OPERATOR_ID, operatorId);
    }

    public JwtToken withClaim(final String key, final String value) {
        builder.claim(key, value);
        return this;
    }

    public JwtToken withRole(final String role) {
        this.roles.add(role);
        return this;
    }

    public String build() {
        builder.claim("rol", roles);
        return builder.compact();
    }

    public JwtToken withRoles(List<String> roles) {
        if (roles != null) {
            this.roles.addAll(roles);
        }
        return this;
    }
}
