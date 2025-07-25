package com.example.openlibrary.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.openlibrary.model.User;
import com.example.openlibrary.repository.UserRepository;


@EnableWebSecurity
@Configuration
public class security_config {
	
	@Autowired
	private LoginSuccessHandler loginsuccessHandler;
	
    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/admin/**", "/profile").hasRole("admin")
            	.requestMatchers("/profile", "/reading").hasRole("member")
                .requestMatchers(
                    "/", "/signup", "/login", "/index",  "/home",  
                    "/css/**", "/js/**", "/oauth2/**", "/categories/**", "/profile/**", "/promo/**", "/search/**", "/user/**", "/reservations/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/index")
                .loginProcessingUrl("/login")
                .successHandler(loginsuccessHandler)
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
        	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
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
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setRole("member");
                userRepository.save(user);
            }

            return new DefaultOAuth2User(
            		List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())),
            		oauth2User.getAttributes(),
            		"email"
           ); 		
        };

    }
}
