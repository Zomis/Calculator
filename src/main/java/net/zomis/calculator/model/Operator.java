package net.zomis.calculator.model;

import java.util.function.DoubleBinaryOperator;

/**
 * Created by Simon on 4/23/2015.
 */
public class Operator {

    private final String key;
    private final DoubleBinaryOperator op;

    public Operator(String key, DoubleBinaryOperator operator) {
        this.key = key;
        this.op = operator;
    }

    public String getKey() {
        return key;
    }

    public DoubleBinaryOperator getOp() {
        return op;
    }

}
