package br.com.diegogusava.rabobankpoa.security;

public interface SecurityConstants {
    String AUTH_LOGIN_URL = "/login";

    // Signing key for HS512 algorithm
    // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
    String JWT_SECRET = "n2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRg";

    // JWT token defaults
    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_TYPE = "JWT";
    String TOKEN_ISSUER = "secure-api";
    String TOKEN_AUDIENCE = "secure-app";
    String TOKEN_OPERATOR_ID = "operator-id";

}
