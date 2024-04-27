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
    private final String userId;

    /**
     * UserCredentials
     *
     * @param userId id
     */
    @JsonCreator
    public UserCredentials(
            final String userId
    ) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

}
