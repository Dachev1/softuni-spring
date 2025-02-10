package com.paintingscollectors.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    public static final Set<String> UNAUTHENTICATED_ENDPOINTS = Set.of("/login", "/register", "/", "/error");
    public static final String USER_ID_FROM_SESSION = "user_id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        boolean isPublic = UNAUTHENTICATED_ENDPOINTS.contains(requestURI);
        boolean isLoggedIn = session != null && session.getAttribute(USER_ID_FROM_SESSION) != null;

        // If NOT logged in and trying to access a non-public endpoint => redirect to /login
        if (!isLoggedIn) {
            if (!isPublic) {
                response.sendRedirect("/login");
                return false;
            }
        }
        // If logged in and trying to access a public endpoint => redirect to /home
        else {
            if (isPublic) {
                response.sendRedirect("/home");
                return false;
            }
        }

        return true;
    }
}
