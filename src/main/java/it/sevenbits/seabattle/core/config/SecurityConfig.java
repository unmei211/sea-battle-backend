package it.sevenbits.seabattle.core.config;

import it.sevenbits.seabattle.core.repository.token.TokenRepository;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
import it.sevenbits.seabattle.core.security.auth.JsonWebTokenService;
import it.sevenbits.seabattle.core.security.auth.JwtSettings;
import it.sevenbits.seabattle.core.security.auth.JwtTokenService;
import it.sevenbits.seabattle.core.security.encrypt.BCryptPasswordEncoder;
import it.sevenbits.seabattle.core.security.encrypt.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityConfig
 */
@Configuration
public class SecurityConfig {
    /**
     * PasswordEncoder bean
     *
     * @return bcryptencoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JwtTokenService
     *
     * @param settings         settings
     * @param tokenRepository  rep
     * @param userRepository   rep
     * @return token service
     */
    @Bean
    public JwtTokenService jwtTokenService(
            final JwtSettings settings,
            final UserRepository userRepository,
            final TokenRepository tokenRepository) {
        return new JsonWebTokenService(
                settings,
                tokenRepository,
                userRepository
        );
    }
}
