package calculator;

import java.util.Scanner;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    public static void processInput() {
        while (true) {
            System.out.print("> ");
            String[] arguments = input().split("\\s");
            try {
                switch (arguments.length) {
                    case 0 : break;
                    case 1 :
                        if ("/exit".equals(arguments[0])) {
                            exit();
                        } else if (arguments[0].isEmpty()) {
                            break;
                        } else {
                            System.out.println(Integer.parseInt(arguments[0]));
                        }
                        break;
                    case 2 : System.out.println(sum(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1])));
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

    private static int sum(int a, int b) {
        return a + b;
    }
}
