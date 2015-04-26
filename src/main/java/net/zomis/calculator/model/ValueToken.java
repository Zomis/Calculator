package net.zomis.calculator.model;

/**
 * Created by Simon on 4/26/2015.
 */
public class ValueToken extends Token {
    private final String value;

    public ValueToken(String value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public String opType() {
        return "VAL";
    }
}
