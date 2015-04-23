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

    public Operator(String key, DoubleBinaryOperator operator) {
        this(key, operator, null);
    }

    public Operator(String key, DoubleBinaryOperator operator, DoubleUnaryOperator unaryOperator) {
        this.key = key;
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
}
