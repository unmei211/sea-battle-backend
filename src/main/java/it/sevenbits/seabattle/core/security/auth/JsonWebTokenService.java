package it.sevenbits.seabattle.core.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import it.sevenbits.seabattle.core.model.token.RefreshToken;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.token.TokenRepository;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
import it.sevenbits.seabattle.core.util.exceptions.NotFoundException;
import it.sevenbits.seabattle.core.util.exceptions.TokenExpiredException;
import it.sevenbits.seabattle.web.model.token.ComplexToken;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * JsonWebTokenService
 */
@AllArgsConstructor
public class JsonWebTokenService implements JwtTokenService {
    private final JwtSettings settings;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public String createAccessToken(final User user) {
        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(now))
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(Date.from(now.plus(settings.getTokenExpirationIn())));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
    }

    @Override
    public String createRefreshToken(final Long playerId) {
        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(now))
                .setSubject(playerId.toString())
                .setExpiration(Date.from(
                        now.plus(settings.getRefreshTokenExpirationIn())
                ));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
    }

    /**
     * expired
     *
     * @param claims claims
     * @return boolean
     */
    private boolean isExpired(final Jws<Claims> claims) {
        Date now = new Date();
        return claims.getBody().getExpiration().getTime() <= now.getTime();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserCredentials parseAccessToken(final String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(token);

        if (isExpired(claims)) {
            throw new TokenExpiredException("tokes is expired");
        }
        String subject = claims.getBody().getSubject();
        return new UserCredentials(Long.valueOf(subject));
    }

    @Override
    public String parseRefreshToken(final String refreshToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(refreshToken);

        if (isExpired(claims)) {
            throw new TokenExpiredException("tokes is expired");
        }

        return claims.getBody().getSubject();
    }

    @Override
    public String createOrUpdateRefreshToken(final User user) {
        String refreshToken = createRefreshToken(user.getId());

        Optional<RefreshToken> oldRefreshToken = tokenRepository.findByUserId(user.getId());
        if (oldRefreshToken.isEmpty()) {
            tokenRepository.save(new RefreshToken(user, refreshToken));
        } else {
            oldRefreshToken.get().setRefreshToken(refreshToken);
            tokenRepository.save(oldRefreshToken.get());
        }

        return refreshToken;
    }

    /**
     * refresh token
     *
     * @param oldRefreshToken token
     * @return token
     */
    public ComplexToken refreshTokens(final String oldRefreshToken) {
        if (!tokenRepository.existsByRefreshToken(oldRefreshToken)) {
            throw new NotFoundException("this token not found");
        }

        parseRefreshToken(oldRefreshToken);

        Long userId = Long.valueOf(parseRefreshToken(oldRefreshToken));

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("user/player not found")
        );

        String refreshToken = createRefreshToken(userId);
        tokenRepository.updateRefreshToken(refreshToken, oldRefreshToken);

        String accessToken = createAccessToken(user);

        return new ComplexToken(accessToken, refreshToken);
    }
}
