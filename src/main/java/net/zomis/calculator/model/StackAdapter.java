package net.zomis.calculator.model;

import java.util.Arrays;
import java.util.Deque;
import java.util.function.ToDoubleFunction;

/**
 * Created by Simon on 4/27/2015.
 */
public class StackAdapter {

    private final Deque<ValueTypeToken> tokens;
    private final CalcContext context;

    public StackAdapter(Deque<ValueTypeToken> tokens, CalcContext context) {
        this.tokens = tokens;
        this.context = context;
    }

    public double pop() {
        ValueTypeToken token = tokens.removeFirst();
        return (Double) token.getValue();
    }

    public double params(int paramCount, ToDoubleFunction<double[]> fnc) {
        double[] params = new double[paramCount];
        for (int i = 0; i < params.length; i++) {
            params[i] = pop();
        }
        System.out.println("Function evaluate: " + Arrays.toString(params));
        return fnc.applyAsDouble(params);
    }


}
