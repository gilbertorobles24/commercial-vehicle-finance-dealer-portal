package com.dealerfinance.loan.security;

import java.util.Collection;

public class CustomUserPrincipal {

    private final String userId;
    private final String dealerId;
    private final Collection<String> roles;

    public CustomUserPrincipal(
            String userId,
            String dealerId,
            Collection<String> roles
    ) {
        this.userId = userId;
        this.dealerId = dealerId;
        this.roles = roles;
    }

    public String getUserId() { return userId; }

    public String getDealerId() { return dealerId; }

    public Collection<String> getRoles() { return roles; }
}
