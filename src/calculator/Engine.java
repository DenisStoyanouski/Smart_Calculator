package calculator;

import java.util.Arrays;
import java.util.Collections;
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
                    case 2 : System.out.println(sum(arguments));
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

    private static int sum(String[] digits) {
        return Arrays.stream(digits).mapToInt(Integer::valueOf).sum();
    }
}
