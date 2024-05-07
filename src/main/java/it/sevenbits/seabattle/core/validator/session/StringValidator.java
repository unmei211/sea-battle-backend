package it.sevenbits.seabattle.core.validator.session;

import org.springframework.stereotype.Component;

@Component
public class StringValidator {

    public boolean validate(final String string) {
        return string != null && !string.isEmpty();
    }
}
