package net.zomis.calculator.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalcTest {

    private static final double DELTA = 0.00001;
    private CalcContext context = new CalcContext();

    @Test
    public void test() throws CalculationException {
        assertEquals(4, context.createExpression("4").evaluate().getValue(), DELTA);
    }

}
