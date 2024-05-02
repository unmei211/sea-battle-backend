package it.sevenbits.seabattle.core.validator.session;

import org.springframework.stereotype.Component;

@Component
public class IdValidator {

    public boolean validate(final Long id) {
        return id != null && id > 0;
    }
}
