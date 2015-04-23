package net.zomis.calculator.main;

import net.zomis.calculator.model.CalcContext;
import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;

import java.util.Scanner;

/**
 * Created by Simon on 4/23/2015.
 */
public class Main {

    public static void main(String[] args) throws CalculationException {
        try (Scanner scanner = new Scanner(System.in)) {
            CalcContext context = new CalcContext();
            while (true) {
                String expression = scanner.nextLine();
                if (expression.isEmpty()) {
                    break;
                }
                Expression expr = context.createExpression(expression);
                System.out.println(expr.evaluate());
            }
        }
    }

}
