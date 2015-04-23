package net.zomis.calculator.model;

import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;
import net.zomis.calculator.model.expressions.OperatorExpression;
import net.zomis.calculator.model.expressions.ValueExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalcContext {

    private static final Pattern VALUE = Pattern.compile("^\\d+(\\.\\d+)?$");

    public Expression createExpression(String expression) throws CalculationException {
        expression = expression.trim();
        Matcher matcher = VALUE.matcher(expression);
        if (matcher.find()) {
            return new ValueExpression(Double.parseDouble(expression));
        }

        // TODO: List of operators and functions
        int opIndex = expression.indexOf('+');
        if (opIndex != -1) {
            String before = expression.substring(0, opIndex);
            String after = expression.substring(opIndex + 1);
            return new OperatorExpression(this, (a, b) -> a + b, before, after);
        }

        throw new CalculationException("Not sure what to do with expression: " + expression);
    }

}
