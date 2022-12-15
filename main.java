/*
 * Main
 *
 * Ver. 1.0.0
 *
 * Free for using, without license
 *
 * Program which implements simple calculator based on Stack Model and
 * converting expression into postfix form.
 */

import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Main class provides general functionality of the program.
 *
 * @author Artur Mukhutdinov
 * @version 1.0.0 15 December 2022
 */
final class OptionalProject {
    private OptionalProject() {
    }

    public static void main(String[] args) {
        Converter converter = new Converter();

        Scanner s = new Scanner(System.in);

        System.out.println("Please, write your expression separated by spaces! (Example: (a + b) - c * d):"
                + "\n" + "Expression in infix (normal) form: ");
        String expression = converter.convertInfixIntoPostfix(s.nextLine());
        System.out.println("Postfix form of your expression: " + expression);

        Calculator calculator = new Calculator();

        Stack<String> stack = new Stack<>();

        String[] separatedExpression = expression.split(" ");

        for (String elem : separatedExpression) {
            if (isSign(elem)) {
                Signs signType = Signs.parseSign(elem.charAt(0));
                makeOperation(signType, stack, calculator);
                continue;
            }

            pushOperandToStack(elem, stack);
        }

        System.out.println("Result of your expression: " + stack.getFirstFromTop());
    }

    private static boolean isSign(String elem) {
        return (elem.length() == 1 && Signs.parseSign(elem.charAt(0)) != null);
    }

    private static void makeOperation(Signs signType, Stack<String> stack, Calculator calculator) {
        switch (signType) {
            case PLUS -> {
                String result = calculator.addition(stack.getFirstFromTop(), stack.getSecondFromTop());
                stack.pop();
                stack.pop();
                stack.push(result);
            }
            case MINUS -> {
                String result = calculator.subtraction(stack.getSecondFromTop(), stack.getFirstFromTop());
                stack.pop();
                stack.pop();
                stack.push(result);
            }
            case MUL -> {
                String result = calculator.multiplication(stack.getFirstFromTop(), stack.getSecondFromTop());
                stack.pop();
                stack.pop();
                stack.push(result);
            }
            case DIV -> {
                String result = calculator.division(stack.getSecondFromTop(), stack.getFirstFromTop());
                stack.pop();
                stack.pop();
                stack.push(result);
            }
            default -> {
            }
        }
    }

    private static void pushOperandToStack(String operand, Stack<String> stack) {
        stack.push(operand);
    }
}

interface Calculations {

    String addition(String operand1, String operand2);

    String subtraction(String operand1, String operand2);

    String multiplication(String operand1, String operand2);

    String division(String operand1, String operand2);
}

class Calculator implements Calculations {
    /**
     * Method convert operands from string type of certain and make calculations.
     * After this method return a string representation of the result.
     * Result of this method - addition of two operands.
     *
     * @param operand1 string representation of operand 1.
     * @param operand2 string representation of operand 2.
     * @return string representation of the result.
     */
    @Override
    public String addition(String operand1, String operand2) {
        try {
            return String.valueOf(Double.parseDouble(operand1) + Double.parseDouble(operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return "";
    }

    /**
     * Method convert operands from string type of certain and make calculations.
     * After this method return a string representation of the result.
     * Result of this method - subtraction of two operands.
     *
     * @param operand1 string representation of operand 1.
     * @param operand2 string representation of operand 2.
     * @return string representation of the result.
     */
    @Override
    public String subtraction(String operand1, String operand2) {
        try {
            return String.valueOf(Double.parseDouble(operand1) - Double.parseDouble(operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return "";
    }

    /**
     * Method convert operands from string type of certain and make calculations.
     * After this method return a string representation of the result.
     * Result of this method - multiplication of two operands.
     *
     * @param operand1 string representation of operand 1.
     * @param operand2 string representation of operand 2.
     * @return string representation of the result.
     */
    @Override
    public String multiplication(String operand1, String operand2) {
        try {
            return String.valueOf(Double.parseDouble(operand1) * Double.parseDouble(operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return "";
    }

    /**
     * Method convert operands from string type of certain and make calculations.
     * After this method return a string representation of the result.
     * Result of this method - division of two operands.
     *
     * @param operand1 string representation of operand 1.
     * @param operand2 string representation of operand 2.
     * @return string representation of the result.
     */
    @Override
    public String division(String operand1, String operand2) {
        try {
            return String.valueOf(Double.parseDouble(operand1) / Double.parseDouble(operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return "";
    }
}

/**
 * Implementation of simple stack model according to canonical stack definition.
 *
 * @param <T> Data type which would be stored in the stack.
 */
class Stack<T> {
    /**
     * Variable which are implements stack itself.
     * Provide a functionality according to canonical stack model.
     */
    private final ArrayList<T> stack = new ArrayList<>();

    /**
     * Add object on the top of the stack.
     *
     * @param object added on the top of the stack.
     */
    public void push(T object) {
        stack.add(object);
    }

    /**
     * Remove object from the top of the stack.
     */
    public void pop() {
        stack.remove(this.depth() - 1);
    }

    /**
     * Return string representation of the stack array and clear stack overall.
     *
     * @return String representation of the stack.
     */
    public String uploadStackInString() {
        StringBuilder string = new StringBuilder();

        if (stack.size() == 0) return "";

        int i = 0;
        string.append(stack.get(i));

        for (i = 1; i < this.depth(); i++) {
            string.append(" ").append(stack.get(i));
        }

        stack.clear();

        return string.toString();
    }

    /**
     * Return string representation of the expression in the LAST GAINED BRACKETS and remove it from the stack.
     *
     * @return String representation of the expression in the LAST GAINED BRACKETS
     */
    public String uploadExpressionInParentsInString() {
        StringBuilder string = new StringBuilder();

        int startIndex = stack.lastIndexOf("(");
        int finishIndex = stack.indexOf(")");

        for (int i = startIndex + 1; i < finishIndex; i++) {
            if (i == startIndex + 1) {
                string.append(stack.get(i));
                continue;
            }

            string.append(" ").append(stack.get(i));
        }
        removeSubsequence(startIndex, finishIndex);

        return string.toString();
    }

    private void removeSubsequence(int start, int finish) {
        int length = finish - start + 1;

        if (length == stack.size()) {
            uploadStackInString();
            length = 0;
        }

        while (length > 0) {
            stack.remove(start);
            length--;
        }
    }

    /**
     * Method return a size of the stack.
     *
     * @return int value of stack size.
     */
    public int depth() {
        return stack.size();
    }

    /**
     * Method return first element of the stack.
     *
     * @return Object of T type from the top of the stack.
     */
    public T getFirstFromTop() {
        return stack.get(this.depth() - 1);
    }

    /**
     * Method return second element of the stack.
     *
     * @return Object of T type from the second place counting from the top of the stack.
     */
    public T getSecondFromTop() {
        return stack.get(this.depth() - 2);
    }
}

interface Conversions {
    /**
     * Function which implements simple translation from Infix --> Postfix form of expression notation.
     *
     * @param expression Expression given in string format.
     * @return Given expression, converted into postfix form, also in string format.
     */
    String convertInfixIntoPostfix(String expression);
}

/**
 * Class provides a several conversions from one type to another.
 * Can be updated.
 */
class Converter implements Conversions {
    /**
     * Variable accessed by whole class and behave itself a counter of amount
     * left brackets from already read line.
     */
    private static int leftParents = 0;
    /**
     * Variable accessed by whole class and behave itself a counter of amount
     * right brackets from already read line.
     */
    private static int rightParents = 0;

    @Override
    public String convertInfixIntoPostfix(String expression) {
        StringBuilder postfixForm = new StringBuilder();

        String[] separatedExpression = expression.split(" ");
        Stack<String> operationsStack = new Stack<>();

        try {
            if (!checkForExpressionsCorrectness(separatedExpression)) {
                throw new InvalidExpression();
            }

            for (int i = 0; i < separatedExpression.length; i++) {
                String currentStringElem = separatedExpression[i];

                if (i == 0 && !(isOperand(currentStringElem))) {
                    throw new InvalidExpression();
                }

                if (isOperand(currentStringElem) && (i % 2 == 0)) {
                    currentStringElem = deleteAndCheckForBrackets(currentStringElem, operationsStack);

                    if (postfixForm.length() == 0) {
                        postfixForm.append(currentStringElem);
                        continue;
                    }

                    postfixForm.append(" ").append(currentStringElem);

                    if (leftParents > 0 && rightParents > 0) {
                        while (Math.min(leftParents, rightParents) > 0) {
                            postfixForm
                                    .append(" ")
                                    .append(reversed(operationsStack.uploadExpressionInParentsInString()));
                            leftParents--;
                            rightParents--;
                        }
                    }

                    continue;
                }


                if (isSign(currentStringElem.charAt(0)) && (i % 2 != 0)) {

                    if (operationsStack.depth() == 0) {
                        operationsStack.push(currentStringElem);
                        continue;
                    }

                    if (
                            Signs.signsComparison(operationsStack.getFirstFromTop().charAt(0), currentStringElem.charAt(0))
                    ) {
                        postfixForm.append(" ").append(reversed(operationsStack.uploadStackInString()));
                        operationsStack.push(currentStringElem);
                    } else {
                        operationsStack.push(currentStringElem);
                    }
                    continue;
                }

                throw new InvalidExpression();
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        postfixForm.append(" ").append(reversed(operationsStack.uploadStackInString()));

        return postfixForm.toString();
    }

    private boolean isOperand(String elem) {
        if (elem.length() == 1) {
            return Character.isDigit(elem.charAt(0));
        }

        return true;
    }

    private boolean isSign(Character elem) {
        return (Signs.parseSign(elem) != null);
    }

    private String reversed(String string) {
        return new StringBuilder(string).reverse().toString();
    }

    private boolean checkForExpressionsCorrectness(String[] separatedExpression) {
        return separatedExpression.length % 2 != 0 && separatedExpression.length > 2;
    }

    private String deleteAndCheckForBrackets(String string, Stack<String> stack) {
        String correctString = string;

        if (string.charAt(0) == '(' && string.charAt(string.length() - 1) == ')') {
            correctString = string.substring(string.lastIndexOf("(") + 1, string.indexOf(")"));
        } else if (string.charAt(string.length() - 1) == ')') {
            correctString = string.substring(0, string.indexOf(")"));

            for (int i = 0; i < string.length() - string.indexOf(")"); i++) {
                rightParents++;
                stack.push(")");
            }

        } else if (string.charAt(0) == '(') {
            correctString = string.substring(string.lastIndexOf("(") + 1);

            for (int i = 0; i < string.lastIndexOf("(") + 1; i++) {
                leftParents++;
                stack.push("(");
            }
        }

        return correctString;
    }
}

enum Signs {
    PLUS,
    MINUS,
    MUL,
    DIV,
    LEFT_BRACKET,
    RIGHT_BRACKET;
    private static final int ASCII_MUL = 42;
    private static final int ASCII_PLUS = 43;
    private static final int ASCII_MINUS = 45;
    private static final int ASCII_DIV = 47;
    private static final int ASCII_LEFT_BRACKET = 40;
    private static final int ASCII_RIGHT_BRACKET = 41;

    public static Signs parseSign(Character elem) {
        return switch (elem) {
            case (ASCII_MUL) -> Signs.MUL;
            case (ASCII_PLUS) -> Signs.PLUS;
            case (ASCII_MINUS) -> Signs.MINUS;
            case (ASCII_DIV) -> Signs.DIV;
            case (ASCII_LEFT_BRACKET) -> Signs.LEFT_BRACKET;
            case (ASCII_RIGHT_BRACKET) -> Signs.RIGHT_BRACKET;
            default -> null;
        };
    }

    // If elem_1 < elem_2 == 0
    // Else == 1
    public static boolean signsComparison(Character elem1, Character elem2) {
        Signs sign1 = parseSign(elem1);
        Signs sign2 = parseSign(elem2);
        if ((sign1 == PLUS || sign1 == MINUS) && (sign2 == MUL || sign2 == DIV)) {
            return false;
        }
        if ((sign1 == LEFT_BRACKET || sign1 == RIGHT_BRACKET) || (sign2 == LEFT_BRACKET || sign2 == RIGHT_BRACKET)) {
            return false;
        }

        return true;
    }
}

class InvalidExpression extends Exception {
    @Override
    public String getMessage() {
        return "Invalid expression\n";
    }
}
