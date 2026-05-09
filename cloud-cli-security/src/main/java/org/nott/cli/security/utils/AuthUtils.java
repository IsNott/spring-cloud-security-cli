package org.nott.cli.security.utils;

import org.nott.cli.security.manager.AuthenticationTokenImpl;
import org.nott.cli.security.model.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUsername() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        if (principal instanceof String) {
            return (String) principal;
        }
        return null;
    }

    public static Users getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object details = authentication.getDetails();
        if (details instanceof Users) {
            return (Users) details;
        }

        // 如果是 AuthenticationTokenImpl
        if (authentication instanceof AuthenticationTokenImpl) {
            AuthenticationTokenImpl token = (AuthenticationTokenImpl) authentication;
            Object userDetails = token.getDetails();
            if (userDetails instanceof Users) {
                return (Users) userDetails;
            }
        }

        return null;
    }

    public static Long getCurrentUserId() {
        Users user = getCurrentUser();
        return user != null ? user.getUserId() : null;
    }

}
