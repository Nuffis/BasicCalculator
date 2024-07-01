package main;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Calculator");
        System.out.println("Enter two numbers");

        // Input first number
        System.out.print("Number 1: ");
        double num1 = scanner.nextDouble();

        // Input second number
        System.out.print("Number 2: ");
        double num2 = scanner.nextDouble();

        // Input operation
        System.out.print("Operation (+, -, *, /): ");
        String operation = scanner.next();

        // Perform operation and display result
        performOperation(num1, num2, operation);

        scanner.close();
    }

    private static void performOperation(double num1, double num2, String operation) {
        double result = 0;

        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero");
                    return;
                }
                break;
            default:
                System.out.println("Error: Invalid operation");
                return;
        }

        // Display the result
        System.out.println("Result: " + result);
    }
}