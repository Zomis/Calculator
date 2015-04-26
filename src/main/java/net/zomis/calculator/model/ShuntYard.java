package net.zomis.calculator.model;

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

    public List<String> convert(String data) {
        List<String> results = new ArrayList<>();

        return results;
    }

}
