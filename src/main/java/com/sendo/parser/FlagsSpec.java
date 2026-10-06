package com.sendo.parser;

import javax.xml.validation.Validator;

public record FlagsSpec(String longName, String shortName, boolean takesValue, Validator validator) {
    public FlagsSpec {
        if (longName == null || longName.isBlank() || longName.startsWith("-") || longName.contains("=")) {
            throw new IllegalArgumentException("bad flag long name: '" + longName + "'");
        }

        if (shortName != null) {
            if (shortName.length() != 1) {
                throw new IllegalArgumentException("short flag must be exactly of one character");
            }
            if (!Character.isLetterOrDigit(shortName.charAt(0))) {
                throw new IllegalArgumentException("bad short flag: '" + shortName + "'");
            }
        }
    }
}
