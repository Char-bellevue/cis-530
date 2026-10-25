/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
*/
package edu.bellevue.cis530.week7.config;

import edu.bellevue.cis530.week7.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class that secures the Student Services API. It ties the database
 * login to BCrypt password checking and sets the role-based access rules.
 */
@Configuration
public class SecurityConfig {

    // Service that loads members from the members table.
    private final CustomUserDetailsService gCustomUserDetailsService;

    /**
     * Creates the configuration with the user details service that Spring injects.
     * @param customUserDetailsService CustomUserDetailsService, the service that loads members.
     */
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.gCustomUserDetailsService = customUserDetailsService;
    } // end SecurityConfig

    /**
     * Creates the provider that checks a typed password against the stored BCrypt hash.
     * No parameters.
     * @return DaoAuthenticationProvider, a provider that uses the members table and BCrypt.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Spring Boot 4.x requires the user details service in the constructor.
        DaoAuthenticationProvider lProvider = new DaoAuthenticationProvider(gCustomUserDetailsService);

        // Tell the provider that stored passwords are BCrypt hashes.
        lProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return lProvider;
    } // end authenticationProvider

    /**
     * Exposes the authentication manager that Spring Security builds.
     * @param configuration AuthenticationConfiguration, the Spring Security authentication settings.
     * @return AuthenticationManager, the manager that handles login attempts.
     * @throws Exception when the manager cannot be created.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    } // end authenticationManager

    /**
     * Defines the security rules for every request. ADMIN members can use GET, POST,
     * PUT, and DELETE, and USER members can only use GET.
     * @param http HttpSecurity, the builder used to set the security rules.
     * @return SecurityFilterChain, the finished set of security rules.
     * @throws Exception when the rules cannot be built.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Use the database provider for every login.
                .authenticationProvider(authenticationProvider())
                // Turn off CSRF so Postman can send POST, PUT, and DELETE requests.
                .csrf(AbstractHttpConfigurer::disable)
                // Set the role-based access rules for the API.
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                // Show the default Spring Security login page in the browser.
                .formLogin(Customizer.withDefaults())
                // Allow Basic Auth so Postman can log in with a username and password.
                .httpBasic(Customizer.withDefaults());
        return http.build();
    } // end securityFilterChain
} // end SecurityConfig
