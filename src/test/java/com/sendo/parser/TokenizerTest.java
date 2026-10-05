package com.sendo.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TokenizerTest {

    @Test
    void tokenizesCreateCommand() {
        List<Token> tokens = Tokenizer.tokenize(
                List.of("create", "mycontainer"));

        assertEquals(2, tokens.size());

        assertEquals(
                new Token.Word("create", 0),
                tokens.get(0));

        assertEquals(
                new Token.Word("mycontainer", 1),
                tokens.get(1));
    }

    @Test
    void tokenizeLongFlag() {
        List<Token> tokens = Tokenizer.tokenize(List.of("create", "mycontainer", "--force"));

        assertEquals(new Token.LongFlag("--force", 2, "force", null), tokens.get(2));
    }

    @Test
    void tokenizeLongFlagAndFollowingWordSeparately() {
        List<Token> tokens = Tokenizer.tokenize(List.of("create", "mycontainer", "--image", "ubuntu"));

        assertEquals(new Token.LongFlag("--image", 2, "image", null), tokens.get(2));

        assertEquals(new Token.Word("ubuntu", 3), tokens.get(3));
    }

    @Test
    void tokenizeLongFlagWithInlineValue() {

        List<Token> tokens = Tokenizer.tokenize(List.of("create", "mycontainer", "--image=ubuntu"));

        assertEquals(new Token.LongFlag("--image=ubuntu", 2, "image", "ubuntu"), tokens.get(2));
    }

    @Test
    void preserveExplicitEmptyInlineValue() {
        List<Token> tokens = Tokenizer.tokenize(List.of("--image="));

        assertEquals(new Token.LongFlag("--image=", 0, "image", ""), tokens.get(0));
    }

    @Test
    void tokenizeShortFlag() {
        List<Token> tokens = Tokenizer.tokenize(List.of("create", "mycontainer", "-f"));

        assertEquals(new Token.ShortFlag("-f", 2, "f"), tokens.get(2));
    }

    @Test
    void tokenizeShortFlagGrouped() {
        List<Token> tokens = Tokenizer.tokenize(List.of("create", "mycontainer", "-rf"));

        assertEquals(new Token.ShortFlag("-rf", 2, "rf"), tokens.get(2));
    }

    @Test
    void tokenizeEverythingsAfterTerminatorAsWord() {
        List<Token> tokens = Tokenizer.tokenize(List.of("exec", "demo", "--", "ls", "-la"));

        assertEquals(new Token.EndOfFlags("--", 2), tokens.get(2));
        assertEquals(new Token.Word("ls", 3), tokens.get(3));
        assertEquals(new Token.Word("-la", 4), tokens.get(4));
    }

    @Test
    void tokenizeSingleDashAsWord() {
        List<Token> tokens = Tokenizer.tokenize(List.of("-"));

        assertEquals(new Token.Word("-", 0), tokens.get(0));
    }

    @Test
    void keepNegetiveNumberAsShortFlags() {
        List<Token> tokens = Tokenizer.tokenize(List.of("--tail", "-1"));

        assertEquals(new Token.LongFlag("--tail", 0, "tail", null), tokens.get(0));
        assertEquals(new Token.ShortFlag("-1", 1, "1"), tokens.get(1));
    }

    @Test
    void preservesOriginalArgumentIndexes() {
        List<Token> tokens = Tokenizer.tokenize(
                List.of(
                        "create",
                        "mycontainer",
                        "--image",
                        "ubuntu"));

        for (int i = 0; i < tokens.size(); i++) {
            assertEquals(i, tokens.get(i).index());
        }
    }
}