package net.zomis.calculator.model;

import java.util.regex.Pattern;

/**
 * Created by Simon on 4/26/2015.
 */
public class ValueToken extends Token {
    private final String value;

    public ValueToken(String value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public String opType() {
        return "VAL";
    }

    private static final Pattern IDENTIFIER = Pattern.compile("^[A-Za-z][A-Za-z\\d_]*$");

    public ValueTypeToken toValueType(CalcContext context) throws CalculationException {
        if (IDENTIFIER.matcher(value).find()) {
            return new ValueTypeToken(double.class, context.variables.get(value).evaluate().getValue());
        }
        return new ValueTypeToken(double.class, Double.parseDouble(value));
    }
}
