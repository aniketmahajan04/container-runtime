package com.sendo.commands;

import com.sendo.parser.CommandRegistry;

public final class Commands {
    private Commands() {
    }

    public static CommandRegistry defaultRegistry() {
        CommandRegistry r = new CommandRegistry();
        r.register(CreateCommand.SPEC());
        return r;
    }

}
