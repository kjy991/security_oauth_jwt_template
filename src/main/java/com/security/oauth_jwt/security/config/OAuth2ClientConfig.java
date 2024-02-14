package com.security.oauth_jwt.security.config;

import com.security.oauth_jwt.security.CustomAuthorityMapper;
import com.security.oauth_jwt.security.service.CustomOAuth2UserService;
import com.security.oauth_jwt.security.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class OAuth2ClientConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;


    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/tokne/**").permitAll()
                        .requestMatchers("/", "/static/js/**", "/static/images/**", "/static/css/**", "/static/scss/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
                        userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService)))
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
        return http.build();
    }

   /*@Bean // hasAuthority 일경우 정의하지 않는다
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper(){
       SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
       simpleAuthorityMapper.setPrefix("ROLE_");
       return simpleAuthorityMapper;
   }*/

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }
}
