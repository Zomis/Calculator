package net.zomis.calculator.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by Simon on 4/23/2015.
 */
@RunWith(Parameterized.class)
public class CalcTest {

    private static final double DELTA = 0.00001;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"4", 4},
                {"4 + 9", 13},
                {"-4 + -4.2", -8.2},
                {"4 * 9", 36},
                {"2 + 7 * 3", 23},
                {"7 * 3 + 2", 23},
                {"7 - 3", 4},
                {"7 / 3", 2.3333333333},
        });
    }

    @Parameterized.Parameter(0)
    public String input;

    @Parameterized.Parameter(1)
    public double expected;

    @Test
    public void test() throws CalculationException {
        CalcContext context = CalcContext.createDefault();
        assertEquals(input, expected, context.createExpression(input).evaluate().getValue(), DELTA);
    }

}
