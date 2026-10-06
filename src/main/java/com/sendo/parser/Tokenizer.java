package com.sendo.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Tokenizer
 * Segregate token based on their type and create an list of tokens.
 * Tokenizer separate token string based these four type
 * a) Word -- e.g., "create", "stop", "exec", "-", etc.
 * b) LongFlag -- e.g., "--memory 512m", "--memory=512m", "--memory=", "--image
 * ubuntu"
 * c) ShortFlag -- e.g., "-p 8080:8080", "-it", "-rf", "-1"
 * d) EndOfFlag -- e.g., "--" terminator after this count as Word type.
 */
public final class Tokenizer {
    private Tokenizer() {
    }

    public static List<Token> tokenize(List<String> args) {

        /* out list that contains all Token types */
        List<Token> out = new ArrayList<>(args.size());

        boolean afterTerminator = false; /*
                                          * afterTerminator ("--") this without following flag treated as terminator
                                          * mean after it all words treated as Word type
                                          */

        for (int i = 0; i < args.size(); i++) {

            String a = args.get(i);

            if (afterTerminator || a.equals("-") || !a.startsWith("-")) {

                /* "create", "stop", "exec", "-" stored as Word record */
                out.add(new Token.Word(a, i));

            } else if (a.equals("--")) {

                /* ("--") after this every thing stores as words */
                out.add(new Token.EndOfFlags(a, i));
                afterTerminator = true;

            } else if (a.startsWith("--")) {
                /* long flags are stored based on values */
                int eq = a.indexOf("=");
                if (eq < 0) {

                    /* flags like "--image", "--memory=" are stored with default values */
                    out.add(new Token.LongFlag(a, i, a.substring(2), null));

                } else {

                    /* flags like "--memory=512m", "--memory 512m" */
                    out.add(new Token.LongFlag(a, i, a.substring(2, eq), a.substring(eq + 1)));

                }
            } else {

                /** we will keep the letters together "-rf", "-t", "-1" */
                out.add(new Token.ShortFlag(a, i, a.substring(1)));

            }

        }

        return List.copyOf(out);

    }
}
