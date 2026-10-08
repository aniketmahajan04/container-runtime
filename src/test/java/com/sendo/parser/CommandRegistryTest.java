package com.sendo.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.sendo.commands.CreateCommand;

public class CommandRegistryTest {

    @Test
    void shouldRegisterAndFindCommand() {
        CommandRegistry registry = new CommandRegistry();
        CommandSpec create = CreateCommand.SPEC();
        registry.register(create);

        Optional<CommandSpec> result = registry.find("create");
        assertTrue(result.isPresent());
        assertEquals(create, result.get());
    }

    @Test
    void shouldReturnEmptyWhenCommandDoesNotExist() {
        CommandRegistry registry = new CommandRegistry();

        assertTrue(registry.find("does-not-exist").isEmpty());
    }

    @Test
    void shouldRejectDuplicateCommand() {
        CommandRegistry registry = new CommandRegistry();

        CommandSpec create = CreateCommand.SPEC();

        registry.register(create);

        assertThrows(IllegalStateException.class, () -> registry.register(create));
    }

    @Test
    void shouldReturnAllRegisteredCommands() {
        CommandRegistry registry = new CommandRegistry();

        CommandSpec create = CreateCommand.SPEC();

        registry.register(create);

        Collection<CommandSpec> commands = registry.all();

        assertEquals(1, commands.size());
        assertTrue(commands.contains(create));

        // Later, with multiple commands:

        // registry.register(CreateCommand.spec());
        // registry.register(StartCommand.spec());
        // registry.register(StopCommand.spec());

        // assertEquals(3, registry.all().size());
    }

    @Test
    void allCommandShouldBeUnmodifiable() {
        CommandRegistry registry = new CommandRegistry();
        CommandSpec create = CreateCommand.SPEC();

        registry.register(create);

        Collection<CommandSpec> commands = registry.all();

        assertThrows(UnsupportedOperationException.class, () -> commands.clear());
    }

}
