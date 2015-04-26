package net.zomis.calculator.model;

/**
 * Created by Simon on 4/26/2015.
 */
public class OperatorToken extends Token {
    private final Operator operator;

    public OperatorToken(String trim, Operator operator) {
        this.operator = operator;
    }

    @Override
    public String getString() {
        return operator.getKey();
    }

    @Override
    public String opType() {
        return "OP";
    }
}
