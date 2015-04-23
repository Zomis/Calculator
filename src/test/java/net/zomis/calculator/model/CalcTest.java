package net.zomis.calculator.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Simon on 4/23/2015.
 */
public class CalcTest {

    private static final double DELTA = 0.00001;
    private final CalcContext context = CalcContext.createDefault();

    @Test
    public void testValue() throws CalculationException {
        assertEquals(4, context.createExpression("4").evaluate().getValue(), DELTA);
    }

    @Test
    public void testAddition() throws CalculationException {
        assertEquals(13, context.createExpression("4 + 9").evaluate().getValue(), DELTA);
    }

}
