package com.sendo.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class CommandRegistry {

    private Map<String, CommandSpec> byName = new LinkedHashMap<>();

    public void register(CommandSpec spec) {

        if (byName.putIfAbsent(spec.name(), spec) != null) {
            throw new IllegalStateException("duplicate key: " + spec.name());
        }
    }

    public Optional<CommandSpec> find(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    public Collection<CommandSpec> all() {
        return Collections.unmodifiableCollection(byName.values());
    }

}
