package calculator;

import java.util.Scanner;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);

    public static void processInput() {
        while (true) {
            String[] arguments = input().split("\\s");
        }

    }

    private static String input() {
        return scanner.nextLine();
    }
    public static int sum(int a, int b) {
        return a + b;
    }
}
