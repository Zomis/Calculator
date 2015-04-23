package net.zomis.calculator.model;

import net.zomis.calculator.model.expressions.OperatorExpression;
import net.zomis.calculator.model.expressions.ValueExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalcContext {

    private static final Pattern VALUE = Pattern.compile("^-?\\d+(\\.\\d+)?$");
    private static final Pattern FUNCTION_CALL = Pattern.compile("^([a-zA-Z]+)\\(");
    private final List<Operator> operators = new ArrayList<>();
    private final Map<String, CalcFunction> functions = new HashMap<>();

    private CalcContext() {}

    public Expression createExpression(String expression) throws CalculationException {
        expression = expression.trim();
        Matcher matcher = VALUE.matcher(expression);
        if (matcher.find()) {
            return new ValueExpression(Double.parseDouble(expression));
        }

        matcher = FUNCTION_CALL.matcher(expression);
        if (matcher.find()) {
            String functionName = matcher.group(1);
            CalcFunction func = functions.get(functionName);
            if (func == null) {
                throw new CalculationException("Unknown function name: " + functionName + " in expression " + expression);
            }
            int rightParen = findFirstMatchingRightParen(expression);
            String params = expression.substring(functionName.length() + 1, rightParen);

            ValueExpression funcResult = func.evaluate(this, params);
            return createExpression(funcResult.getValue() + " " + expression.substring(rightParen + 1));
        }

        for (Operator op : operators) {
            String key = op.getKey();
            int opIndex = expression.indexOf(key);
            if (opIndex != -1) {
                String before = expression.substring(0, opIndex);
                if (before.contains("(")) {
                    continue;
                }
                String after = expression.substring(opIndex + key.length());
                return new OperatorExpression(this, op, before, after);
            }
        }

        throw new CalculationException("Not sure what to do with expression: '" + expression + "'");
    }

    private static int findFirstMatchingRightParen(String str) throws CalculationException {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '(') {
                count++;
            }
            if (ch == ')') {
                count--;
                if (count == 0) {
                    return i;
                }
                if (count < 0) {
                    throw new CalculationException("Unexpected right paren: " + str);
                }
            }
        }
        return -1;
    }

    public static CalcContext createDefault() {
        CalcContext context = new CalcContext();
        context.functions.put("abs", new CalcFunction(exp -> exp.length == 1, exp -> Math.abs(exp[0].getValue())));

        context.operators.add(new Operator("+", (a, b) -> a + b));
        context.operators.add(new Operator("-", (a, b) -> a - b, a -> -a));

        context.operators.add(new Operator("*", (a, b) -> a * b));
        context.operators.add(new Operator("/", (a, b) -> a / b));
        return context;
    }

}
