package com.bonappetit.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private long userId;
    private String username;

    public boolean isLoggedIn() {
        return userId != 0;
    }

    public void login(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public void logout() {
        this.userId = 0;
        this.username = null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
