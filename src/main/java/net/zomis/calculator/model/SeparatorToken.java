package net.zomis.calculator.model;

/**
 * Created by Simon on 4/26/2015.
 */
public class SeparatorToken extends Token {
    private final char ch;

    public SeparatorToken(char ch) {
        this.ch = ch;
    }

    @Override
    public String getString() {
        return Character.toString(ch);
    }

    @Override
    public String opType() {
        return "SEP";
    }
}
