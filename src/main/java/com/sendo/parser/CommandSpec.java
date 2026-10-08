package com.sendo.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 
 * CommandSpec
 * 
 * @param name
 * @param positionals
 * @param flags
 *
 *                    CommandSpec:- The complete grammer of one command, as
 *                    plain data
 *                    This CommandSpec know nothing about running command.
 *                    positionals - the fixed, required, named positionals, in
 *                    order
 *                    flags:- the flags this command accepts
 * 
 */
public record CommandSpec(String name, List<PositionalSpec> positionals, List<FlagsSpec> flags) {

    public CommandSpec {
        if (name == null || name.isBlank() || name.startsWith("-") || name.chars().anyMatch(Character::isWhitespace)) {
            throw new IllegalArgumentException("bad command name: '" + name + "'");
        }

        positionals = List.copyOf(positionals);
        flags = List.copyOf(flags);

        Set<String> names = new HashSet<>();
        for (PositionalSpec p : positionals) {
            if (!names.add(p.name())) {
                throw new IllegalArgumentException(name + ": duplicate postional <" + p.name() + ">");
            }
        }

        Set<String> longs = new HashSet<>();
        Set<String> shorts = new HashSet<>();

        for (FlagsSpec fs : flags) {
            if (!longs.add(fs.longName())) {
                throw new IllegalArgumentException(name + ": duplicate flag --" + fs.longName());
            }

            if (!shorts.add(fs.shortName())) {
                throw new IllegalArgumentException(name + ": duplicate short flag -" + fs.shortName());
            }
        }

    }

    public Optional<FlagsSpec> findLong(String longName) {
        return flags.stream().filter(f -> f.longName().equals(longName)).findFirst();
    }

}
