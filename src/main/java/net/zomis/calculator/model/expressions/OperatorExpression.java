package net.zomis.calculator.model.expressions;

import net.zomis.calculator.model.CalcContext;
import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;
import net.zomis.calculator.model.Operator;

/**
 * Created by Simon on 4/23/2015.
 */
public class OperatorExpression extends Expression {

    private final Operator operator;
    private final Expression before;
    private final Expression after;

    public OperatorExpression(CalcContext context, Operator operator, String before, String after) throws CalculationException {
        this.operator = operator;
        if (before.trim().isEmpty()) {
            this.before = null;
        } else {
            this.before = context.createExpression(before);
        }
        this.after = context.createExpression(after);
    }

    @Override
    public ValueExpression evaluate() throws CalculationException {
        if (before == null) {
            return new ValueExpression(operator.getUnaryOperator().applyAsDouble(after.evaluate().getValue()));
        } else {
            return new ValueExpression(operator.getOp().applyAsDouble(
                    before.evaluate().getValue(), after.evaluate().getValue())
            );
        }
    }

}
