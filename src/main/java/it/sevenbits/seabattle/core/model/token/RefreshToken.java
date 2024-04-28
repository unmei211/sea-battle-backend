package it.sevenbits.seabattle.core.model.token;

import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * model
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private User user;

    @Column
    private String refreshToken;

    public RefreshToken(
            final User user,
            final String refreshToken
    ) {
        this.user = user;
        this.refreshToken = refreshToken;
    }
}
