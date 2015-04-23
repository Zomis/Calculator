package net.zomis.calculator.model.expressions;

import net.zomis.calculator.model.Expression;

/**
 * Created by Simon on 4/23/2015.
 */
public class ValueExpression extends Expression {

    private final double value;

    public ValueExpression(double value) {
        this.value = value;
    }

    @Override
    public ValueExpression evaluate() {
        return new ValueExpression(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
