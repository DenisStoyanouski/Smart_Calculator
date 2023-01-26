package calculator;

import java.util.Scanner;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    public static void processInput() {
        while (true) {
            //System.out.print("> ");
            String[] arguments = input().split("\\s+");
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
                String[] operations = currentOperator.split("");
                for(String operation : operations) {
                    switch(operation) {
                        case "+" -> currentResult = add(currentResult, Integer.parseInt(currentSymbol));
                        case "-" -> currentResult = subtract(currentResult, Integer.parseInt(currentSymbol));
                    }
                }
            } else {
                currentOperator = currentSymbol.replaceAll("--", "+");
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
