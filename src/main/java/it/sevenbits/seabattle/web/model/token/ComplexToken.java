package it.sevenbits.seabattle.web.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token with refresh and access
 */
@AllArgsConstructor
@Getter
public class ComplexToken {
    private final String accessToken;
    private final String refreshToken;
}
