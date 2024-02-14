package com.security.oauth_jwt.security.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Builder
@Data
public class User {

    private String registrationId;
    private String id;
    private String username;
    private String password;
    private String provider;
    private String email;
    private List<? extends GrantedAuthority> authorities;


}
