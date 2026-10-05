package com.sendo.parser;

import java.util.ArrayList;
import java.util.List;

public final class Tokenizer {
    private Tokenizer() {
    }

    public static List<Token> tokenize(List<String> args) {

        List<Token> out = new ArrayList<>(args.size());
        boolean afterTerminator = false;

        for (int i = 0; i < args.size(); i++) {

            String a = args.get(i);

            if (afterTerminator || a.equals("-") || !a.startsWith("-")) {
                out.add(new Token.Word(a, i));
            } else if (a.equals("--")) {
                out.add(new Token.EndOfFlags(a, i));
                afterTerminator = true;
            } else if (a.startsWith("--")) {
                int eq = a.indexOf("=");
                if (eq < 0) {
                    out.add(new Token.LongFlag(a, i, a.substring(2), null));
                } else {
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
