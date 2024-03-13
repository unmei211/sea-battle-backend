package it.sevenbits.seabattle.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 * session model witch needs two users
 */
@Getter
@Setter
public class SessionModel {
    private Long userFirst;
    private Long userSecond;
}
