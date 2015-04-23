package net.zomis.calculator.model;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalculationException extends Exception {

    public CalculationException() {
    }

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculationException(Throwable cause) {
        super(cause);
    }

}
