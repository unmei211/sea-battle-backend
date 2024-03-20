package it.sevenbits.seabattle.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * user without password
 */
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private int rating;
}
