package com.security.oauth_jwt.security.service;

import com.security.oauth_jwt.security.model.ProviderUser;
import com.security.oauth_jwt.security.model.User;
import com.security.oauth_jwt.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public void register(String registrationId, ProviderUser providerUser) {
        User user = User.builder()
                .registrationId(registrationId)
                .id(providerUser.getId())
                .username(providerUser.getUsername())
                .provider(providerUser.getPassword())
                .email(providerUser.getEmail())
                .authorities(providerUser.getAuthorities())
                .build();
        userRepository.register(user);
    }
}
