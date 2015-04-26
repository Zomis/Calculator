package net.zomis.calculator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            for (Operator op : context.operators) {
                opLength = match(data, end, op.getKey()) ? op.getKey().length() : opLength;
            }
            for (String op : context.functions.keySet()) {
                opLength = match(data, end, op) && data.charAt(end + op.length()) == '('
                        ? op.length() + 1 : opLength;
            }
            if (data.charAt(end) == ',') {
                opLength = 1;
            }
            if (data.charAt(end) == ')') {
                opLength = 1;
            }
            if (data.charAt(end) == '(') {
                opLength = 1;
            }

            if (opLength > 0) {
                String op = data.substring(i, end).trim();
                if (!op.isEmpty()) {
                    results.add(new Token(op, false));
                }

                String valueString = data.substring(end, end + opLength).trim();
                if (!valueString.isEmpty()) {
                    results.add(new Token(valueString, true));
                }
                end += opLength - 1;
                i = end + 1;
            }
        }

        String lastPart = data.substring(i).trim();
        if (!lastPart.isEmpty()) {
            results.add(new Token(lastPart, false));
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
