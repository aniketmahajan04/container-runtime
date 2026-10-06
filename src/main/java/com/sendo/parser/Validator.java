package com.sendo.parser;

import java.util.Optional;

@FunctionalInterface
public interface Validator {

    /**
     * @return empty if the value is acceptable, otherwise a human-readable reason
     */
    Optional<String> check(String raw);

    /* it accepts anything */
    Validator ANY = raw -> Optional.empty();
}
