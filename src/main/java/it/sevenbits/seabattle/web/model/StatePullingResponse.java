package it.sevenbits.seabattle.web.model;

import lombok.Getter;

/**
 * not use
 */
@Getter
public class StatePullingResponse {
    private String state;

    public StatePullingResponse(String state) {
        this.state = state;
    }
}
