package net.zomis.calculator.main;

import net.zomis.calculator.model.CalcContext;
import net.zomis.calculator.model.CalculationException;
import net.zomis.calculator.model.Expression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Simon on 4/23/2015.
 */
public class Main {

    public static void main(String[] args) throws CalculationException {
        if (args.length == 1) {
            File file = new File(args[0]);
            if (file.exists()) {
                try {
                    runFile(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to read file '" + file + "'");
                }
            } else {
                System.out.println(run(args[0]));
            }
            return;
        } else if (args.length > 0) {
            System.out.println(run(String.join(" ", args)));
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            CalcContext context = CalcContext.createDefault();
            while (true) {
                String expression = scanner.nextLine();
                if (expression.isEmpty()) {
                    break;
                }
                try {
                    Expression expr = context.createExpression(expression);
                    System.out.println(expr.evaluate());
                } catch (CalculationException ex) {
                    ex.printStackTrace();
                    System.out.println("Calculation Error: " + ex.getMessage());
                }
            }
        }
    }

    private static double run(String arg) throws CalculationException {
        CalcContext context = CalcContext.createDefault();
        return context.createExpression(arg).evaluate().getValue();
    }

    public static List<Double> runFile(InputStream testCalc) throws CalculationException {
        List<Double> results = new ArrayList<>();
        try (Scanner scanner = new Scanner(testCalc)) {
            CalcContext context = CalcContext.createDefault();
            while (scanner.hasNextLine()) {
                String expression = scanner.nextLine();
                Expression expr = context.createExpression(expression);
                double result = expr.evaluate().getValue();
                System.out.println(result);
                results.add(result);
            }
        }
        return results;
    }
}
