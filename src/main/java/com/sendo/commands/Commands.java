package com.sendo.commands;

import com.sendo.parser.CommandRegistry;

/***
 * 
 * Commands
 * A central class that hold command registry.
 * Every time we create any command we can add that command inside
 * CommandRegistry one by one.
 */
public final class Commands {
    private Commands() {
    }

    public static CommandRegistry defaultRegistry() {
        CommandRegistry r = new CommandRegistry();
        r.register(CreateCommand.SPEC());
        // In future
        // r.register(StartCommand.SPEC());
        return r;
    }

}
