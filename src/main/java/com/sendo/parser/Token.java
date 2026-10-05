package com.sendo.parser;

/*
* Token What one raw argument look like
* A token is classified by SHAPE ONLY. It never carries the meaning of that depends on a command's
* spec (e.g "this is the value of --timout"). That decision is belong to the matcher.
*
* Every token keeps
* raw   - the original text, untouched (the matcher needs it for "--tail -1"
* index - the position in the original args list (for error message)
*/

public sealed interface Token {
    String raw();

    int index();

    /* A bare word: "start" , "demo", "5", "-", and EVERYTHING after a "--". */
    record Word(String raw, int index) implements Token {}

    /**
    * "--force" or "--timeout=5"
    * inliveValue is null when there was no '=': "" means means as explicit empty value ("--timeout=")
    */
    record LongFlag(String raw, int index, String name, String inlineValue) implements Token {}

    /**
    * "-t", "-rf". The letters stay grouped: only the matcher (which knows the spec) can tell
    * a bundle of booleans ("-rf") from a value of ("-t").
    */
    record ShortFlag(String raw, int index, String letters) implements Token {}

    /** The "--" marker. Every token after it is a Word. */
    record EndOfFlags(String raw, int index) implements Token {}

}
