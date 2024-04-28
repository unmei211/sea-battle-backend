package it.sevenbits.seabattle.core.config;

import it.sevenbits.seabattle.core.security.auth.JwtAuthInterceptor;
import it.sevenbits.seabattle.core.security.auth.JwtTokenService;
import it.sevenbits.seabattle.core.security.auth.UserCredentialsResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final JwtTokenService jwtTokenService;


    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserCredentialsResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(
                new JwtAuthInterceptor(jwtTokenService)
        );
    }
}