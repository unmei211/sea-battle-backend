package it.sevenbits.seabattle.core.security.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * UserCredentials
 */
public class UserCredentials implements IUserCredentials {
    @JsonProperty("userId")
    private final Long userId;

    /**
     * UserCredentials
     *
     * @param userId id
     */
    @JsonCreator
    public UserCredentials(
            final Long userId
    ) {
        this.userId = userId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

}
