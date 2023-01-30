package com.abcyb101.notetakerprojectv2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.abcyb101.notetakerprojectv2.security.old.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class WebSecurityConfig {//https://www.baeldung.com/security-spring
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {// DEVELOPMENT ONLY, NOT PRODUCTIVE!
		UserDetails user = User.withDefaultPasswordEncoder()//Spring User
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsPasswordService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.authorizeRequests()
    	.antMatchers("/", "/login", "/register").permitAll()
    	.antMatchers("/admin/**").hasAnyAuthority("ADMIN") //potential Mapping
    	.antMatchers("/account/**").hasAnyAuthority("USER")//potential Mapping
    	.antMatchers("/api/v1/helloworld/**").permitAll()//dev and test
    	.anyRequest().authenticated()
    	.and()
    	// form login
    	.csrf().disable().formLogin()
    	.loginPage("/login")
    	.failureUrl("/login?error=true")
    	//.successHandler(sucessHandler)// if we want to redirect to a different page upon successful auth
    	.usernameParameter("username")
    	.passwordParameter("password")
    	.and()
    	//logout
    	.logout()
    	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    	.logoutSuccessUrl("/")
    	.and()
    	.exceptionHandling()
    	.accessDeniedPage("/access-denied");
    	
    	http.authenticationProvider(authenticationProvider());
    	http.headers().frameOptions().sameOrigin();//research into this
    	
    	return http.build();
    	
    	//simplified
    	/*
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()	//for specific URLS: .antMatchers("/message") //It will authorize only the request having URL contains /message . for other URL it will not ask for authorization.
            )
            .httpBasic(withDefaults());
        return http.build();
        */
    }

}