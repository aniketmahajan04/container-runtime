package com.sendo.parser;

import java.util.List;

public record CommandSpec(String name, List<PositionalSpec> positionals, List<FlagsSpec> flags) {

}
