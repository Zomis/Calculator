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


    @Test
    public void tokenize() throws Exception {
//        String input = "7 + abs(x - 2 * 5) / y + 4 * abs(3 - 9)";
        String input = "7+abs(x-2.5221*5)/variableName+4*abs(3.363-9.21)";
        ShuntYard shunt = new ShuntYard(CalcContext.createDefault());
        List<Token> tokens = shunt.tokenize(input);
        List<String> expected = Arrays.asList("7","+","abs(","x","-","2.5221","*","5",")","/",
                "variableName","+","4","*","abs(","3.363","-","9.21",")");
        List<String> actual = tokens.stream().map(Token::getString).collect(Collectors.toList());
        assertEquals(expected, actual);
    }
}
