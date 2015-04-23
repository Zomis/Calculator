package net.zomis.calculator.model;

import net.zomis.calculator.model.expressions.ValueExpression;

/**
 * Created by Simon on 4/23/2015.
 */
public class Expression {

    public Expression() {
    }

    public ValueExpression evaluate() throws CalculationException {
        throw new UnsupportedOperationException(this.toString());
    }

}
