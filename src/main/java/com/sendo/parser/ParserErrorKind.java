package com.sendo.parser;

/**
 * Every way a command line can be syntactically wrong. Tests assert on these,
 * not on messages.
 */
public enum ParserErrorKind {
    MISSING_COMMAND,
    UNKNOWN_COMMAND,
    UNKNOWN_FLAG,
    MISSING_ARGUMENT,
    TOO_MANY_ARGUMENTS,
    MISSING_FLAG_VALUE,
    INVALID_VALUE,
    BAD_FLAG_BUNDLE,
    MISSING_REQUIRED_FLAG
}
