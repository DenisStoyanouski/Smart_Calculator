package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {

    private final static Scanner scanner = new Scanner(System.in);
    final private static Pattern number = Pattern.compile("[-+]?\\d+");
    final private static Pattern varName = Pattern.compile("[a-zA-Z]+");
    final private static Pattern variable = Pattern.compile("[a-zA-Z]+=?[-+]?((\\d+)|([a-zA-Z]*?))");
    final private static Pattern expression = Pattern.compile("[-+(]?(\\d|[a-zA-Z])+([-+*/^]?[(]*?(\\d+|[a-zA-Z])[)]*?)*");
    final private static Pattern command = Pattern.compile("/[a-zA-Z]+");
    final private static Map<String, BigInteger> variables = new HashMap<>();

    public static void processInput() {

        while (true) {
            String line = input().replaceAll("(\\++)|((-{2})+)", "+")
                    .replaceAll("(\\+-)|(-\\+)", "-")
                    .replaceAll("\\s+","");
            Matcher matcherCom = command.matcher(line);
            Matcher matcherEx = expression.matcher(line);
            Matcher matcherNumber = number.matcher(line);
            Matcher matcherVariable = variable.matcher(line);

            if (line.isEmpty()) {
                continue;
            } else if (matcherNumber.matches()){
                BigInteger number = new BigInteger(line);
                System.out.println(number);
            } else if (matcherCom.matches()) {
                getCommand(line);
            } else if (matcherVariable.matches()) {
                getVariable(line);
            } else {
                if (matcherEx.matches() && areBracketsClosed(line)) {
                    System.out.println(calculate(line));
                } else {
                    System.out.println("Invalid expression");
                }

            }
        }
    }

    private static boolean areBracketsClosed(String line) {
        Deque<String> expression = new ArrayDeque<>();
        String[] exp = line.replaceAll("\\s+","").split("(?!\\d)|(?<!\\d)");
        Arrays.stream(exp).filter(x->")".equals(x) || ("(".equals(x)))
                .forEach((x) -> {
                    if ("(".equals(x)) {
                        expression.push(x);
                    } else if (")".equals(x) && !expression.isEmpty()) {
                        expression.pop();
                    } else {
                        expression.push(x);
                    }
                });
        return expression.isEmpty();
    }

    private static void getVariable(String line) {
        String var;
        String value;

        Pattern identifier = Pattern.compile("[a-zA-Z]+\\s*?");
        Pattern assignment = Pattern.compile("(\\s*?([+-]?\\d+|[+-]?[a-zA-Z]+)\\s*?)");
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
                value = line.replaceAll(identifier + "=", "").strip();
                if (value.matches(number.pattern())) {
                    variables.put(var, new BigInteger(value));
                } else {
                    if (variables.containsKey(value)) {
                        variables.put(var, variables.get(value));
                    } else {
                        System.out.println("Unknown variable");
                    }
                }
            }
        }
    }

    private static void getCommand(String line) {
        if (line.matches("/exit")) {
            exit();
        } else if (line.matches("/help")) {
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

    private static BigInteger calculate(String line) {
        String[] conLine = convert(line);
        Deque<String> expressionPostfix = infixToPostfix(conLine);
        Deque<BigInteger> postfix = new ArrayDeque<>();
        while (!expressionPostfix.isEmpty()) {
            if (expressionPostfix.peekLast().matches(number.pattern())) {
                postfix.push(new BigInteger(expressionPostfix.pollLast()));
            } else {
                String operator = expressionPostfix.pollLast();
                BigInteger b = postfix.poll();
                BigInteger a = postfix.poll();

                switch(operator) {
                    case "+" : postfix.push(a.add(b));
                        break;
                    case "-" : postfix.push(a.subtract(b));
                        break;
                    case "*" : postfix.push(a.multiply(b));
                        break;
                    case "/" : postfix.push(a.divide(b));
                        break;
                    case "^" : postfix.push(a.pow(Integer.parseInt(b.toString())));
                        break;
                    default : break;
                }
            }
        }
        return postfix.peek();
    }

    private static String[] convert(String line) {
        String[] expression = line.split("(?!(\\d+)|([a-zA-Z]+))|((?<!(\\d)|([a-zA-Z])))");
        for (int i = 0; i < expression.length; i++) {
            if (expression[i].matches(varName.pattern())) {
                expression[i] = String.valueOf(variables.get(expression[i]));
            }
        }
        return expression;
    }

    private static Deque infixToPostfix(String[] infix) {

        Deque<String> operation = new ArrayDeque<>();
        Deque<String> postfix = new ArrayDeque<>();
        Map<String, Integer> priority = new HashMap<>();
        priority.put("^", 3);
        priority.put("*", 2);
        priority.put("/", 2);
        priority.put("+", 1);
        priority.put("-", 1);
        priority.put("(", 0);

        for (String sym : infix) {
            String symbol = sym.strip();
            if ("(".equals(symbol)) {
                operation.push(symbol);
            } else if (")".equals(symbol)) {
                while (!"(".equals(operation.peek())) {
                    postfix.push(operation.poll());
                }
                operation.poll();
            } else if (symbol.matches(number.pattern())) {
                postfix.push(symbol);
            } else if (operation.isEmpty() || priority.get(operation.peek()) < priority.get(symbol)) {
                operation.push(symbol);
            } else if (priority.get(operation.peek()) >= priority.get(symbol)) {
                while (!operation.isEmpty() && priority.get(symbol) <= priority.get(operation.peek())) {
                    postfix.push(operation.poll());
                }
                operation.push(symbol);
            }
        }
        while (!operation.isEmpty()) {
            postfix.push(operation.poll());
        }
        return postfix;
    }

}
