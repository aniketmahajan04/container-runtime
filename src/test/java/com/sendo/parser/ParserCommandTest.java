package com.sendo.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.sendo.commands.CreateCommand;

public class ParserCommandTest {

    @Test
    void shouldReturnCommandName() {
        // CommandSpec spec =
        // CreateCommand.builder().name("create").positionals(List.of()).flags(List.of()).build();
        ParsedCommand parsed = new ParsedCommand(CreateCommand.SPEC(), Map.of(),
                Map.of());

        assertEquals("create", parsed.name());
    }

    @Test
    void shouldReturnPositionalValue() {
        ParsedCommand parsed = new ParsedCommand(CreateCommand.SPEC(), Map.of("name", "demo"), Map.of());

        assertEquals("demo", parsed.positional("name"));
    }

    @Test
    void shouleRejectUndeclaredPositional() {
        ParsedCommand parsed = new ParsedCommand(CreateCommand.SPEC(), Map.of(), Map.of());

        assertThrows(IllegalArgumentException.class, () -> parsed.positional("image"));
    }

    @Test
    void shouldReturnTrueWhenBooleanFlagWasSupplied() {
        FlagsSpec force = new FlagsSpec("force", "f", false, Validator.ANY);

        CommandSpec spec = new CommandSpec("create", List.of(), List.of(force));

        ParsedCommand parsed = new ParsedCommand(spec, Map.of(), Map.of("force", ""));

        assertTrue(parsed.flag("force"));
    }

    @Test
    void shouldReturnFalseWhenBooleanFlagWasNotSupplied() {
        FlagsSpec force = new FlagsSpec("force", "f", false, Validator.ANY);

        CommandSpec spec = new CommandSpec("create", List.of(), List.of(force));

        ParsedCommand parsed = new ParsedCommand(spec, Map.of(), Map.of());

        assertFalse(parsed.flag("force"));
    }

    @Test
    void shouldReturnFlagValue() {
        FlagsSpec timeout = new FlagsSpec("timeout",
                "t", true, Validator.ANY);

        CommandSpec spec = new CommandSpec("create", List.of(), List.of(timeout));

        ParsedCommand parsed = new ParsedCommand(spec, Map.of(), Map.of("timeout", "5"));

        assertEquals("5",
                parsed.flagValue("timeout").orElseThrow());
    }

    @Test
    void shouldRejectValueFlagAsBoolean() {
        FlagsSpec timeout = new FlagsSpec("timeout", "t", true, Validator.ANY);

        CommandSpec spec = new CommandSpec("create", List.of(), List.of(timeout));

        ParsedCommand parse = new ParsedCommand(spec, Map.of(), Map.of("timeout", "5"));

        assertThrows(IllegalArgumentException.class, () -> parse.flag("timeout"));
    }

    @Test
    void shouldRejectBooleanFlagAsValue() {
        FlagsSpec force = new FlagsSpec("force", "f", false, Validator.ANY);

        CommandSpec spec = new CommandSpec("create", List.of(), List.of(force));

        ParsedCommand parsed = new ParsedCommand(spec, Map.of(), Map.of(
                "force", ""));

        assertThrows(IllegalArgumentException.class, () -> parsed.flagValue("force"));
    }
}
