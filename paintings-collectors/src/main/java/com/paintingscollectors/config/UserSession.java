package com.paintingscollectors.config;

import com.paintingscollectors.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clear() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public String username() {
        return this.currentUser.getUsername();
    }
}

