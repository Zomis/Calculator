package net.zomis.calculator.model;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by Simon on 4/23/2015.
 */
public class Operator {

    private final String key;
    private final DoubleBinaryOperator op;
    private final DoubleUnaryOperator unaryOperator;
    private final Associativity associativity;
    private final int precendence;

    public Operator(String key, Associativity associativity, int precendence, DoubleBinaryOperator operator) {
        this(key, associativity, precendence, operator, null);
    }

    public Operator(String key, Associativity associativity, int precendence, DoubleBinaryOperator operator, DoubleUnaryOperator unaryOperator) {
        this.key = key;
        this.associativity = associativity;
        this.precendence = precendence;
        this.op = operator;
        this.unaryOperator = unaryOperator;
    }

    public String getKey() {
        return key;
    }

    public DoubleBinaryOperator getOp() {
        return op;
    }

    public DoubleUnaryOperator getUnaryOperator() {
        return unaryOperator;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public int getPrecendence() {
        return precendence;
    }
}
