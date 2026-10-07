package com.sendo.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.sendo.parser.CommandSpec;

public class CreateCommandTest {

    @Test
    void declaresCreateCommand() {
        CommandSpec spec = CreateCommand.SPEC();

        assertEquals("create", spec.name());

        assertEquals(1, spec.positionals().size());

        assertEquals("name", spec.positionals().get(0).name());

        assertTrue(spec.flags().isEmpty());
    }

}
