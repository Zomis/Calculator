package net.zomis.calculator.model.expressions;

import net.zomis.calculator.model.CalcContext;
import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;

/**
 * Created by Simon on 4/24/2015.
 */
public class VariableExpression extends Expression {

    private final String expression;
    private final CalcContext context;

    public VariableExpression(CalcContext context, String expression) {
        this.expression = expression;
        this.context = context;
    }

    @Override
    public ValueExpression evaluate() throws CalculationException {
        return context.createExpression(expression).evaluate();
    }
}
