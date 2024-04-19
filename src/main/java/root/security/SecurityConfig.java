package root.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                req -> req
                    /*.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")*/
                    /*.requestMatchers("/member/**").authenticated()*/
                    .requestMatchers("/swagger-ui.html").permitAll()
                    .anyRequest().permitAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .addFilterBefore(
                jwtTokenFilter, UsernamePasswordAuthenticationFilter.class
            )
            .exceptionHandling(
                exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint(authenticationEntryPoint)
            )
        ;

        return http.build();
    }
}
