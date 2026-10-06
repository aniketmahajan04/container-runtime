package com.sendo.parser;

import javax.xml.validation.Validator;

public record PositionalSpec(String name, Validator validator) {

    public PositionalSpec {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("positional needs a name");
        }

        if (validator == null) {
            throw new IllegalArgumentException("positional <" + name + "> needs a validator");
        }
    }

    public static PositionalSpec of(String name) {
        return new PositionalSpec(name, Validator.);
    }

}
