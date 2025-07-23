package com.example.openlibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;


@EnableWebSecurity
@Configuration
public class security_config {
	
    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/signup", "/login", "/index",  "/home",
                 // ✅ allow contact form + submission
                    "/contact", "/contact/send",      
                    "/css/**", "/js/**", "/img/**", 
                    "/oauth2/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/index")
                .loginProcessingUrl("/index")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/index")
                .defaultSuccessUrl("/home", true)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/index?logout").permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/contact/send") 
            );

        return http.build();
    }
    
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

            // Get user info
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");

            // Save to DB if not already saved
            User existingUser = userRepository.findByEmail(email);
            if (existingUser == null) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setRole("member");
                userRepository.save(newUser);
            }

            return oauth2User;
        };
    }

}
