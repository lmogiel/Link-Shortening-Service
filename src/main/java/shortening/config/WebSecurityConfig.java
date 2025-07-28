package shortening.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import shortening.service.BasicUserDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
    private BasicUserDetailsService userService;
	
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	        .csrf(CsrfConfigurer::disable)
	        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) 
	            .authorizeHttpRequests(authorize -> authorize
	            	.requestMatchers("/app/registration","/swagger/**","/v3/**").permitAll()	
	                .requestMatchers("/app/**", "/shorten/alias").authenticated()
	                .anyRequest().permitAll()
	            ) 
	            .sessionManagement(session -> session
	                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                )
	            
	            .httpBasic(Customizer.withDefaults());
	        
	        return http.build();
	    }
	    
	   
}
