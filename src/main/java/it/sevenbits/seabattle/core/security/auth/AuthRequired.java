package it.sevenbits.seabattle.core.security.auth;

import java.lang.annotation.*;

/**
 * Annotation on controller methods
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequired {
}
