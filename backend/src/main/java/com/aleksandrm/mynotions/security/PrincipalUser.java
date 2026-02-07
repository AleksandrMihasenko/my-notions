package com.aleksandrm.mynotions.security;

import lombok.Data;

@Data
public class PrincipalUser {
    private Long id;
    private String email;

    public PrincipalUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
