package com.sendo.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * CommandRegistry
 * A command registry hold the map that store commands
 * with key, value pairs
 * e.g., <"create", CommandSpec>
 */
public final class CommandRegistry {

    private Map<String, CommandSpec> byName = new LinkedHashMap<>();

    /**
     * 
     * @param spec
     *             regiter method add the new command in map if its absent in
     *             registry
     */
    public void register(CommandSpec spec) {

        if (byName.putIfAbsent(spec.name(), spec) != null) {
            throw new IllegalStateException("duplicate key: " + spec.name());
        }
    }

    /**
     * Return a command spec from "byName" linked hash map
     * it take the key name and finds it in hash map
     * 
     * @param name
     * @return
     */
    public Optional<CommandSpec> find(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    /**
     * This return all the command spec from hashmap
     * 
     * @return
     */
    public Collection<CommandSpec> all() {
        return Collections.unmodifiableCollection(byName.values());
    }

}
