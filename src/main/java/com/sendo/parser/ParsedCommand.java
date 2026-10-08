package com.sendo.parser;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The parser's success value: plain data, already validated. No tokens, no raw
 * args[].
 *
 * Handlers read it BY NAME. The accessors fail fast (IllegalArgumentException)
 * if a handler asks
 * for a name the spec never declared: that is a spec/handler mismatch, a
 * programmer bug that
 * tests should catch, not a user error.
 */

public record ParsedCommand(CommandSpec spec, Map<String, String> positionals, Map<String, String> flags) {

    public ParsedCommand {
        positionals = Collections.unmodifiableMap(new LinkedHashMap<>(positionals));
        flags = Collections.unmodifiableMap(new LinkedHashMap<>(flags));
    }

    public String name() {
        return spec.name();
    }

    public String positional(String positionalName) {

        boolean declared = spec.positionals().stream().anyMatch(p -> p.name().equals(positionalName));

        if (!declared) {
            throw new IllegalArgumentException(spec.name() + " declares no positional <" + positionalName + ">");
        }

        return positionals.get(positionalName);
    }

    public boolean flag(String longName) {
        FlagsSpec f = declared(longName);
        if (f.takesValue()) {
            throw new IllegalArgumentException("--" + longName + " takes a value");
        }

        return flags.containsKey(longName);
    }

    public Optional<String> flagValue(String longName) {
        FlagsSpec f = declared(longName);
        if (!f.takesValue()) {
            throw new IllegalArgumentException("--" + longName + " is a boolean flag");
        }

        return Optional.ofNullable(flags.get(longName));
    }

    private FlagsSpec declared(String longName) {
        return spec.findLong(longName)
                .orElseThrow(() -> new IllegalArgumentException(spec.name() + " declares no flag --" + longName));
    }
}
