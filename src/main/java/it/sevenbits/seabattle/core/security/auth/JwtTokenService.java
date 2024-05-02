package it.sevenbits.seabattle.core.security.auth;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.web.model.token.ComplexToken;

/**
 * JwtTokenService
 */
public interface JwtTokenService {
    /**
     * credentials
     *
     * @param token token
     * @return credentials
     */
    IUserCredentials parseAccessToken(String token);

    /**
     * create refresh token
     *
     * @param userId id
     * @return token
     */
    String createRefreshToken(Long userId);

    /**
     * parse refresh token
     *
     * @param refreshToken refresh token
     * @return userId
     */
    String parseRefreshToken(String refreshToken);

    /**
     * create or update
     *
     * @param user user
     * @return String refresh token
     */
    String createOrUpdateRefreshToken(User user);

    /**
     * refresh
     *
     * @param oldRefreshToken old
     * @return bundle refreshToken and accessToken
     */
    ComplexToken refreshTokens(String oldRefreshToken);
    /**
     * @param user user
     * @return token
     */
    String createAccessToken(User user);
}
