package net.zomis.calculator.model;

import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;
import net.zomis.calculator.model.expressions.OperatorExpression;
import net.zomis.calculator.model.expressions.ValueExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalcContext {

    private static final Pattern VALUE = Pattern.compile("^\\d+(\\.\\d+)?$");
    private final List<Operator> operators = new ArrayList<>();

    private CalcContext() {}

    public Expression createExpression(String expression) throws CalculationException {
        expression = expression.trim();
        Matcher matcher = VALUE.matcher(expression);
        if (matcher.find()) {
            return new ValueExpression(Double.parseDouble(expression));
        }

        // TODO: List of operators and functions
        for (Operator op : operators) {
            String key = op.getKey();
            int opIndex = expression.indexOf(key);
            if (opIndex != -1) {
                String before = expression.substring(0, opIndex);
                String after = expression.substring(opIndex + key.length());
                return new OperatorExpression(this, op.getOp(), before, after);
            }
        }

        throw new CalculationException("Not sure what to do with expression: " + expression);
    }

    public static CalcContext createDefault() {
        CalcContext context = new CalcContext();
        context.operators.add(new Operator("+", (a, b) -> a + b));
        context.operators.add(new Operator("*", (a, b) -> a * b));
        return context;
    }

}
