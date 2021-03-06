package net.zomis.calculator.model;

import net.zomis.calculator.model.expressions.OperatorExpression;
import net.zomis.calculator.model.expressions.ValueExpression;
import net.zomis.calculator.model.expressions.VariableExpression;

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
    private static final Pattern IDENTIFIER = Pattern.compile("^[A-Za-z][A-Za-z\\d_]*$");
    private static final Pattern FUNCTION_CALL = Pattern.compile("([a-zA-Z]+)\\(");
    final List<Operator> operators = new ArrayList<>();
    final Map<String, CalcFunction> functions = new HashMap<>();
    final Map<String, Expression> variables = new HashMap<>();

    private CalcContext() {}

    public Expression createExpression(String expression) throws CalculationException {
        expression = expression.trim();
        Matcher matcher = VALUE.matcher(expression);
        if (matcher.find()) {
            return new ValueExpression(Double.parseDouble(expression));
        }

        matcher = IDENTIFIER.matcher(expression);
        if (matcher.find()) {
            if (!variables.containsKey(expression)) {
                throw new CalculationException("Unknown identifier: " + expression);
            }
            return variables.get(expression);
        }

        matcher = FUNCTION_CALL.matcher(expression);
        if (matcher.find()) {
            String functionName = matcher.group(1);
            CalcFunction func = functions.get(functionName);
            if (func == null) {
                throw new CalculationException("Unknown function name: " + functionName + " in expression " + expression);
            }
            int rightParen = findFirstMatchingRightParen(expression);
            String params = expression.substring(matcher.start(1) + functionName.length() + 1, rightParen);
            String afterRightParen = expression.substring(rightParen + 1);
            String beforeFunction = expression.substring(0, matcher.start(1));

            ValueExpression funcResult = func.evaluate(this, params);
            return createExpression(beforeFunction + funcResult.getValue() + " " + afterRightParen);
        }

        int paren = expression.indexOf('(');
        if (paren != -1) {
            int rightParen = findFirstMatchingRightParen(expression);
            String params = expression.substring(paren + 1, rightParen);
            String afterRightParen = expression.substring(rightParen + 1);
            String beforeFunction = expression.substring(0, paren);

            ValueExpression funcResult = createExpression(params).evaluate();
            return createExpression(beforeFunction + funcResult.getValue() + " " + afterRightParen);
        }

        if (expression.contains("=")) {
            String key = "=";
            int opIndex = expression.indexOf(key);
            if (opIndex != -1) {
                String before = expression.substring(0, opIndex);
                String after = expression.substring(opIndex + key.length());
                VariableExpression var = new VariableExpression(this, after);
                variables.put(before.trim(), var);
                return var;
            }
        }

        for (Operator op : operators) {
            String key = op.getKey();
            int opIndex = expression.lastIndexOf(key);
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
        context.functions.put("abs", new CalcFunction(s -> Math.abs(s.pop()), pred -> pred.length == 1, s -> Math.abs(s[0].getValue()) ));
        context.functions.put("if", new CalcFunction(s -> s.params(3, a -> a[0] > 0 ? a[1] : a[2]),
           exp -> exp.length == 3, exp -> exp[0].getValue() > 0 ? exp[1].getValue() : exp[2].getValue()));

        context.operators.add(new Operator("+", Associativity.LEFT, 2, (a, b) -> a + b));
        context.operators.add(new Operator("-", Associativity.LEFT, 2, (a, b) -> a - b, a -> -a));

        context.operators.add(new Operator("*", Associativity.LEFT, 3, (a, b) -> a * b));
        context.operators.add(new Operator("/", Associativity.LEFT, 3, (a, b) -> a / b));

        context.operators.add(new Operator("^", Associativity.RIGHT, 5, (a, b) -> Math.pow(a, b)));
        return context;
    }

}
