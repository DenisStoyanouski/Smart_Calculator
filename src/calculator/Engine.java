package calculator;

import java.util.Scanner;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    public static void processInput() {
        while (true) {
            String[] arguments = input().split("\\s");
            switch (arguments.length) {
                case 0 : break;
                case 1 :
                    if ("/exit".equals(arguments[0])) {
                        exit();
                    } else {
                        System.out.println(arguments[0]);
                    }
                    break;
                case 2 :
                    try {
                        System.out.println(sum(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1])));
                    } catch (NumberFormatException e) {
                        System.out.println("You need input two digits");
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

    private static int sum(int a, int b) {
        return a + b;
    }
}
