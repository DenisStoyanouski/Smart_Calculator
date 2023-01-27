package calculator;

import jdk.jshell.Snippet;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    private static String line;

    private static Pattern number = Pattern.compile("(-|\\+)?\\d*");
    private static Pattern expression = Pattern.compile("(-|\\+)?\\d+(\\s+?[+-]+\\s+?\\d+)*");
    private static Pattern command = Pattern.compile("/[a-zA-Z]+");


    private static Map<String, Integer> variables = new HashMap<>();

    public static void processInput() {

        while (true) {
            line = input();
            Matcher matcherCom = command.matcher(line);
            Matcher matcherEx = expression.matcher(line);
            Matcher matcherNumber = number.matcher(line);

            if (line.isEmpty()) {
                continue;
            } else if (matcherNumber.matches()){
                int number = Integer.parseInt(line);
                System.out.println(number);
            } else if (matcherCom.matches()) {
                getCommand(line);
            } else if (line.matches("[a-zA-Z].*")) {
                getVariable(line);
            } else {
                if (matcherEx.matches()) {
                    System.out.println(calculate(line));
                } else {
                    System.out.println("Invalid expression");
                }

            }
        }
    }

    private static void getVariable(String line) {
        String var;
        int value;

        Pattern identifier = Pattern.compile("[a-zA-Z]*\\s*?");
        Pattern assignment = Pattern.compile("(\\s*?\\d+\\s*?)|([a-zA-Z]*\\s*?)");
        Pattern expression = Pattern.compile(identifier + "=" + assignment);

        Matcher matcherVar = identifier.matcher(line);
        Matcher mExpression = expression.matcher(line);
        if (line.matches("[^=]+")) {
            if (matcherVar.matches()) {
                var = line.strip();
                if (variables.containsKey(var)) {
                    System.out.println(variables.get(var));
                } else {
                    System.out.println("Unknown variable");
                }
            } else {
                System.out.println("Invalid identifier");
            }
        } else {
            Matcher mIdentifier = identifier.matcher(line).region(0, line.indexOf("="));
            Matcher mAssignment = assignment.matcher(line).region(line.indexOf("=") + 1, line.length());
            if (!mIdentifier.matches()) {
                System.out.println("Invalid identifier");
            } else if (!mAssignment.matches()){
                System.out.println("Invalid assignment");
            } else if (mExpression.matches()) {
                var = line.replaceAll("=" + assignment, "").strip();
                value = Integer.parseInt(line.replaceAll(identifier + "=", "").strip());
                variables.put(var, value);
            }
        }
    }

    private static void getCommand(String line) {
        if (line.matches("\\s*/exit\\s*")) {
            exit();
        } else if (line.matches("\\s*/help\\s*")) {
            System.out.println("The program calculates the sum and subtraction of numbers");
        } else {
            System.out.println("Unknown command");
        }
    }

    private static String input() {
        return scanner.nextLine();
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private static int calculate(String line) {
        String[] expression = line.split("\\s+");
        //System.out.print(Arrays.toString(expression));
        int currentResult = Integer.parseInt(expression[0]);
        String currentOperator = "+";
        for (int i = 1; i < expression.length; i++) {
            String currentSymbol = expression[i];
            if (currentSymbol.matches("\\d+")) {
                switch(currentOperator) {
                    case "+" : currentResult = add(currentResult, Integer.parseInt(currentSymbol));
                    break;
                    case "-" : currentResult = subtract(currentResult, Integer.parseInt(currentSymbol));
                    break;
                    default : break;
                }
            } else {
                /*Pattern patternPlus = Pattern.compile("([+]+)|((-{2})+)");
                Matcher matcher = patternPlus.matcher(currentSymbol);*/
                currentOperator = currentSymbol.replaceAll("(\\++)|((-{2})+)", "+");
                currentOperator = currentOperator.replaceAll("(\\+-)|(-\\+)", "-");
            }
        }
        return currentResult;
    }

    private static int add(int a, int b) {
        return a + b;
    }

    private static int subtract(int a, int b) {
        return a - b;
    }
}
