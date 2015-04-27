package net.zomis.calculator.model;

import net.zomis.calculator.model.expressions.ValueExpression;

import java.util.*;

/**
 * Created by Simon on 4/26/2015.
 */
public class ShuntYard {

    private final CalcContext context;

    public ShuntYard(CalcContext context) {
        this.context = context;
    }

    public List<Token> tokenize(String data) {
        List<Token> results = new ArrayList<>();
        int i = 0;

        // find the first operator or function call
        // extract a token out of it
        // move i to the new position

        // example token separators: '+', 'mod', 'func(', '(', ')'
        int end = i;
        for (; end < data.length(); end++) {
            if (data.charAt(end) == ' ') {
                continue;
            }
            if (Character.isDigit(data.charAt(end))) {
                continue;
            }
            if (data.charAt(end) == '.') {
                continue;
            }

            int opLength = -1;
            Token token = null;
            final int currEnd = end;

            if (data.charAt(end) == ',' || data.charAt(end) == '(' || data.charAt(end) == ')') {
                opLength = 1;
                token = new SeparatorToken(data.charAt(end));
            } else {
                Optional<Operator> operator = context.operators.stream().filter(op -> match(data, currEnd, op.getKey())).findFirst();
                if (operator.isPresent()) {
                    opLength = operator.get().getKey().length();
                    token = new OperatorToken(data.substring(end, end + opLength).trim(), operator.get());
                } else {
                    Optional<Map.Entry<String, CalcFunction>> function = context.functions.entrySet()
                            .stream()
                            .filter(fnc -> match(data, currEnd, fnc.getKey()))
                            .filter(fnc -> data.charAt(currEnd + fnc.getKey().length()) == '(')
                            .findFirst();

                    if (function.isPresent()) {
                        opLength = function.get().getKey().length() + 1;
                        token = new FunctionToken(function.get().getKey(), function.get().getValue());
                    }
                }
            }

            if (token != null) {
                String op = data.substring(i, end).trim();
                if (!op.isEmpty()) {
                    results.add(new ValueToken(op));
                } else if (token instanceof OperatorToken) {
                    if (results.isEmpty() || !results.get(results.size() - 1).getString().equals(")")) {
                        OperatorToken operatorToken = (OperatorToken) token;
                        token = new OperatorToken(operatorToken.getOperator().getKey(), operatorToken.getOperator().asUnary());
                    }
                }
                results.add(token);
                end += opLength - 1;
                i = end + 1;
            }
        }

        String lastPart = data.substring(i).trim();
        if (!lastPart.isEmpty()) {
            results.add(new ValueToken(lastPart));
        }

        return results;
    }

    private boolean match(String data, int pos, String key) {
        return data.regionMatches(pos, key, 0, key.length());
    }

    public List<Token> shuntYard(List<Token> tokens) throws CalculationException {
        List<Token> output = new ArrayList<>();
        Deque<Token> stack = new LinkedList<>();

        for (Token token : tokens) {

            if (token instanceof ValueToken) {
                output.add(token);
            }
            if (token instanceof FunctionToken) {
                stack.push(token);
                stack.push(new SeparatorToken('('));
            }
            if (token instanceof SeparatorToken) {
                SeparatorToken sep = (SeparatorToken) token;
                if (sep.getString().equals(",")) {
                    do {
                        output.add(stack.pop());
                    } while (!isLeftParen(stack.peek()));
                    output.add(token);
                }
                if (sep.getString().equals("(")) {
                    stack.push(token);
                }
                if (sep.getString().equals(")")) {
                    while (!isLeftParen(stack.peek())) {
                        output.add(stack.pop());
                    }

                    stack.pop();

                    if (stack.peek() instanceof FunctionToken) {
                        output.add(stack.pop());
                    }
                }
            }
            if (token instanceof OperatorToken) {
                OperatorToken o1 = (OperatorToken) token;
                while (stack.peek() instanceof OperatorToken) {
                    OperatorToken o2 = (OperatorToken) stack.peek();
                    boolean leftAssoc = o1.isLeftAssociative() && o1.getPrecendence() <= o2.getPrecendence();
                    boolean rightAssoc = o1.isRightAssociative() && o1.getPrecendence() < o2.getPrecendence();
                    if (leftAssoc || rightAssoc) {
                        output.add(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() instanceof SeparatorToken) {
                throw new CalculationException("mismatched paranthesis");
            }
            output.add(stack.pop());
        }

        return output;
    }

    private boolean isLeftParen(Token peek) {
        if (!(peek instanceof SeparatorToken)) {
            return false;
        }
        SeparatorToken sep = (SeparatorToken) peek;
        return sep.getString().equals("(");
    }

    public ValueTypeToken performReversePolishNotation(List<Token> tokens) throws CalculationException {
        Deque<ValueTypeToken> stack = new LinkedList<>();
        StackAdapter stacks = new StackAdapter(stack, context);
        for (Token token : tokens) {
            if (token instanceof OperatorToken) {
                OperatorToken op = (OperatorToken) token;
                stack.push(op.getOperator().apply(stacks));
            } else if (token instanceof FunctionToken) {
                FunctionToken func = (FunctionToken) token;
                ValueTypeToken result = func.getFunction().evaluate(context, stacks);
                stack.push(result);
            } else if (token instanceof SeparatorToken) {
                System.out.println("separator - action ??????");
            } else if (token instanceof ValueToken) {
                ValueToken value = (ValueToken) token;
                stack.push(value.toValueType(context));
            } else if (token instanceof ValueTypeToken) {
                ValueTypeToken value = (ValueTypeToken) token;
                stack.push(value);
            } else {
                throw new IllegalStateException("Unknown token type: " + token);
            }
            System.out.println(token + " stack contains: " + stack);
        }
        return stack.pop();
    }


}
