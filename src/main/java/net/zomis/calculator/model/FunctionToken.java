package net.zomis.calculator.model;

import java.util.Map;

/**
 * Created by Simon on 4/26/2015.
 */
public class FunctionToken extends Token {
    private final CalcFunction function;
    private final String name;

    public FunctionToken(String name, CalcFunction function) {
        this.name = name;
        this.function = function;
    }

    @Override
    public String getString() {
        return name + "(";
    }

    @Override
    public String opType() {
        return "FNC";
    }
}
