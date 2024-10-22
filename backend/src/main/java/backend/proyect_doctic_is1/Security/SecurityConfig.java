package backend.proyect_doctic_is1.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {   

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(request -> 
            request.requestMatchers("/api/publications/download/{id}")
            .authenticated()
            .requestMatchers("/api/publications/**")
            .permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(crsf -> crsf.disable());
        
        return http.build();
    }

    @Bean
    public UserDetailsService testUser(){
        User.UserBuilder user = User.builder();
        UserDetails user1 = user.username("juan")
                    .password(passwordEncoder().encode("abc123"))
                    .roles()
                    .build();
        return new InMemoryUserDetailsManager(user1);
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
}