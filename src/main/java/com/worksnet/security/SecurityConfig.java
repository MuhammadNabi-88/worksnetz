package com.worksnet.security;

import com.worksnet.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/jobs/{id:\\d+}", "/login", "/register", "/css/**", "/error").permitAll()
				.anyRequest().authenticated())
			.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/dashboard", true).permitAll())
			.logout(logout -> logout.logoutSuccessUrl("/").permitAll());
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService userDetailsService(UserRepository users) {
		return username -> users.findByUsername(username)
			.map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).roles("USER").build())
			.orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + username));
	}
}
