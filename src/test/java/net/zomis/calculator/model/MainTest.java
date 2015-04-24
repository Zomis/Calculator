package net.zomis.calculator.model;

import net.zomis.calculator.main.Main;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Simon on 4/24/2015.
 */
public class MainTest {

    @Test
    public void test() throws CalculationException {
        Main.main(new String[]{ "4", "*", "2", "+", "3" });
    }

    @Test
    public void fileTest() throws CalculationException {
        List<Double> results = Main.runFile(getClass().getResourceAsStream("TestCalc"));
        assertEquals(Arrays.asList(11.0), results);
    }

}
