package calculator;

import java.util.Scanner;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    public static void processInput() {
        while (true) {
            //System.out.print("> ");
            String[] arguments = input().split("\\s");
            try {
                switch (arguments.length) {
                    case 0 : break;
                    case 1 :
                        if ("/exit".equals(arguments[0])) {
                            exit();
                        } else if ("/help".equals(arguments[0])){
                            System.out.println("The program calculates the sum of numbers");
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
    private static String input() {
        return scanner.nextLine();
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    private static int calculate(String[] expression) {

        int currentResult = Integer.parseInt(expression[0]);
        String currentOperator = "+";
        for (int i = 1; i < expression.length; i++) {
            String currentSymbol = expression[i];
            if (currentSymbol.matches("\\d")) {
                switch (currentOperator) {
                    case "+" -> currentResult = addition(currentResult, Integer.parseInt(currentSymbol));
                    case "-" -> currentResult = subtraction(currentResult, Integer.parseInt(currentSymbol));
                    default -> System.out.println("Unknown operation");
                }
            } else if (currentSymbol.matches("[+-/*]")) {
                currentOperator = currentSymbol;
            }
        }
        return currentResult;
    }

    private static int addition(int a, int b) {
        return a + b;
    }

    private static int subtraction(int a, int b) {
        return a - b;
    }
}
