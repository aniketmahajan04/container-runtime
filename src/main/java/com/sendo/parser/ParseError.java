package com.sendo.parser;

/**
 * One problem with a command line. argIndex is the position in the original
 * args list,
 * or -1 when the problem has no position (like "missing <name>").
 */
public record ParseError(ParserErrorKind kind, String message, int argIndex) {
}
