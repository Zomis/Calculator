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

    public boolean isLeftAssociative() {
        return operator.getAssociativity() == Associativity.LEFT;
    }

    public boolean isRightAssociative() {
        return operator.getAssociativity() == Associativity.RIGHT;
    }

    public int getPrecendence() {
        return operator.getPrecendence();
    }

    public Operator getOperator() {
        return operator;
    }
}
