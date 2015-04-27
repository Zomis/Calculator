package net.zomis.calculator.model;

import net.zomis.calculator.model.expressions.ValueExpression;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalcFunction {

    private final Predicate<ValueExpression[]> predicate;
    private final ToDoubleFunction<ValueExpression[]> function;
    private final ToDoubleFunction<StackAdapter> fnc;

    public CalcFunction(ToDoubleFunction<StackAdapter> fnc, Predicate<ValueExpression[]> predicate, ToDoubleFunction<ValueExpression[]> function) {
        this.predicate = predicate;
        this.function = function;
        this.fnc = fnc;
    }

    public ValueTypeToken evaluate(CalcContext context, StackAdapter stack) throws CalculationException {
        return new ValueTypeToken(double.class, fnc.applyAsDouble(stack));
    }

    public ValueExpression evaluate(CalcContext context, String paramData) throws CalculationException {
        String[] params = paramData.split(",");
        ValueExpression[] expressions = new ValueExpression[params.length];

        for (int i = 0; i < params.length; i++) {
            expressions[i] = context.createExpression(params[i]).evaluate();
        }

        if (!predicate.test(expressions)) {
            throw new CalculationException("Function does not support input: " + paramData);
        }

        return new ValueExpression(function.applyAsDouble(expressions));
    }

}
