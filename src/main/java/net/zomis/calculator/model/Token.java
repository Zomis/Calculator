package net.zomis.calculator.model;

/**
 * Created by Simon on 4/26/2015.
 */
public abstract class Token {
    public Token() {
    }

    @Override
    public String toString() {
        return opType() + ':' + getString();
    }

    public abstract String getString();
    public abstract String opType();
}
