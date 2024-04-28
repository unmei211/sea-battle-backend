package it.sevenbits.seabattle.core.repository.token;

import it.sevenbits.seabattle.core.model.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefreshToken(String refreshToken);
    Boolean existsByUserId(Long userId);

    @Modifying
    @Query("UPDATE RefreshToken" +
            " SET refreshToken = :newRefreshToken " +
            "WHERE refreshToken = :oldRefreshToken")
    void updateRefreshToken(
            @Param("newRefreshToken") String newRefreshToken,
            @Param("oldRefreshToken") String oldRefreshToken
    );

    Optional<RefreshToken> findByUserId(Long userId);
}
