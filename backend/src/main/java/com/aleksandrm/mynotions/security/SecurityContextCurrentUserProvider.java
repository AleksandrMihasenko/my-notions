package com.aleksandrm.mynotions.security;

import com.aleksandrm.mynotions.service.CurrentUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextCurrentUserProvider implements CurrentUserProvider {
    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof PrincipalUser)) {
            throw new IllegalStateException("Authenticated principal is not PrincipalUser");
        }
        return ((PrincipalUser) principal).getId();
    }
}
