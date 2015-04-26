package net.zomis.calculator.model;

/**
 * Created by Simon on 4/26/2015.
 */
public class Token {
    private final String str;
    private final boolean op;

    public Token(String string, boolean operator) {
        this.str = string;
        this.op = operator;
    }

    @Override
    public String toString() {
        return (op ? "OP:" : "VAL:") + str;
    }
}
