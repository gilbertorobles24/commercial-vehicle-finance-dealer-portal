package com.dealerfinance.loan.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;

public class CustomUserDetails {

    private final Jwt jwt;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
        this.jwt = jwt;
        this.authorities = authorities;
    }

    public Long getDealerId() {
        // Typical claim name in TFS-like systems: "dealer_id", "dealer_number", "sub", etc.
        // Adjust to your mock/real token
        Object dealerIdClaim = jwt.getClaim("dealer_id");
        if (dealerIdClaim instanceof Number) {
            return ((Number) dealerIdClaim).longValue();
        }
        throw new IllegalStateException("dealer_id claim missing or invalid in JWT");
    }

    public String getUsername() {
        return jwt.getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Helper to use in controller
    public static CustomUserDetails from(JwtAuthenticationToken token) {
        return new CustomUserDetails(token.getToken(), token.getAuthorities());
    }
}