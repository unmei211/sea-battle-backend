package it.sevenbits.seabattle.core.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves {@link IUserCredentials} method arguments
 */
public class UserCredentialsResolver implements HandlerMethodArgumentResolver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(IUserCredentials.class);
    }

    @Override
    public IUserCredentials resolveArgument(
            final @NonNull MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final @NonNull NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        try {
            logger.debug("Getting UserCredentials from request attribute");
            return (IUserCredentials) webRequest.getAttribute(
                    JwtAuthInterceptor.USER_CREDENTIALS, RequestAttributes.SCOPE_REQUEST
            );
        } catch (Exception e) {
            return null;
        }
    }

}
