package net.zomis.calculator.model;

/**
 * Created by Simon on 4/27/2015.
 */
public class ValueTypeToken extends Token {
    private final Class<?> type;
    private final Object value;

    public ValueTypeToken(Class<?> type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getString() {
        return String.valueOf(value);
    }

    @Override
    public String opType() {
        return "TYPE-" + type.getSimpleName();
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }

}
