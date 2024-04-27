package it.sevenbits.seabattle.core.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Spring interceptor for JWT based authentication and authorization
 */
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {
    /**
     * user credentials name
     */
    public static final String USER_CREDENTIALS = "userCredentialsAttr";

    private final JwtTokenService jwtService;

    /**
     * JwtAuthInterceptor
     *
     * @param jwtService jwtService
     */
    public JwtAuthInterceptor(
            final JwtTokenService jwtService
    ) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final Object handler
    ) {
        if (handler.getClass().equals(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            return checkAuthorization(method, request, response);
        }

        return true;
    }

    private IUserCredentials getUserCredentials(final HttpServletRequest request) {
        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (header == null || !header.startsWith("Bearer ")) {
                return null;
            }

            final int bearerIndex = 7;
            String token = header.substring(bearerIndex);
            IUserCredentials credentials = jwtService.parseAccessToken(token);
            log.debug("Found credentials in Authorization header: {}", credentials.getUserId());
            request.setAttribute(USER_CREDENTIALS, credentials);
            return credentials;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean checkAuthorization(
            final Method method,
            final HttpServletRequest request,
            final HttpServletResponse response
    ) {
        try {
            IUserCredentials userCredentials = getUserCredentials(request);
            if (method.isAnnotationPresent(AuthRequired.class)) {

                if (userCredentials == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}