package calculator;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    private static String line;

    public static void processInput() {

        while (true) {
            line = input();
            if (line.isEmpty() || isExpressionOrCommand(line)) {
                String[] arguments = line.split("\\s+");
                try {
                    switch (arguments.length) {
                        case 0 : break;
                        case 1 :
                            if ("/exit".equals(arguments[0])) {
                                exit();
                            } else if ("/help".equals(arguments[0])){
                                System.out.println("The program calculates the sum and subtraction of numbers");
                            } else if (arguments[0].isEmpty()) {
                                break;
                            } else {
                                System.out.println(Integer.parseInt(arguments[0]));
                            }
                            break;
                        default: System.out.println(calculate(arguments));
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("You need input digits");
                }
            }
        }

    }
    private static String input() {
        return scanner.nextLine();
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private static boolean isExpressionOrCommand(String line) {
        boolean isValid = true;
        Pattern expression = Pattern.compile(".?\\d+(\\s+?[+-]+\\s+?\\d+)*");
        Pattern command = Pattern.compile("(/exit)|(/help)");
        Matcher matcherCom = command.matcher(line);
        Matcher matcherEx = expression.matcher(line);
        if (line.matches("/.+") && !matcherCom.matches()) {
            System.out.println("Unknown command");
            isValid = false;
        } else if (!line.matches("/.+") && !matcherEx.matches()) {
            System.out.println("Invalid expression");
            isValid = false;
        }
        return isValid;
    }

    private static int calculate(String[] expression) {
        //System.out.print(Arrays.toString(expression));
        int currentResult = Integer.parseInt(expression[0]);
        String currentOperator = "+";
        for (int i = 1; i < expression.length; i++) {
            String currentSymbol = expression[i];
            if (currentSymbol.matches("\\d+")) {
                switch(currentOperator) {
                    case "+" -> currentResult = add(currentResult, Integer.parseInt(currentSymbol));
                    case "-" -> currentResult = subtract(currentResult, Integer.parseInt(currentSymbol));
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
