package net.zomis.calculator.model.expressions;

import net.zomis.calculator.model.CalcContext;
import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;

import java.util.function.DoubleBinaryOperator;

/**
 * Created by Simon on 4/23/2015.
 */
public class OperatorExpression extends Expression {

    private final DoubleBinaryOperator operator;
    private final Expression before;
    private final Expression after;

    public OperatorExpression(CalcContext context, DoubleBinaryOperator operator, String before, String after) throws CalculationException {
        this.operator = operator;
        this.before = context.createExpression(before);
        this.after = context.createExpression(after);
    }

    @Override
    public ValueExpression evaluate() {
        return new ValueExpression(operator.applyAsDouble(before.evaluate().getValue(), after.evaluate().getValue()));
    }

}
