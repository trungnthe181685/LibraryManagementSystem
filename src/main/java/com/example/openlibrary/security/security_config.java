package com.example.openlibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


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
                    "/oauth2/**", "/admin/**", "/books/**", "/categories/**", "/profile/**", "/promo/**", "/search/**", "/user/**", "/reservations/**"
                ).permitAll()
                .requestMatchers("/admin/**").access((authentication, context) -> {
                    HttpServletRequest request = context.getRequest();
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        Object userObj = session.getAttribute("user");
                        if (userObj instanceof User user && "ADMIN".equalsIgnoreCase(user.getRole())) {
                            return new AuthorizationDecision(true);
                        }
                    }
                    return new AuthorizationDecision(false);
                })
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
            	    .logoutUrl("/logout")
            	    .logoutSuccessUrl("/index")
            	    .permitAll()
            	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")))
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
            User user = userRepository.findByEmail(email);
            if (user == null) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setRole("member");
                userRepository.save(newUser);
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // You can now use it in your Thymeleaf templates

            return oauth2User;
        };
    }

}
