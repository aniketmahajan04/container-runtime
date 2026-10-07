package com.sendo.commands;

import java.util.List;

import com.sendo.parser.CommandSpec;
import com.sendo.parser.PositionalSpec;

public class CreateCommand {

    private CreateCommand() {
    }

    public static CommandSpec SPEC() {

        return new CommandSpec("create", List.of(
                PositionalSpec.of("name")), List.of());
    }
}
