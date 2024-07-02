package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

    private StringBuilder currentInput = new StringBuilder();
    private double currentNumber = 0;
    private double previousNumber = 0;
    private char currentOperator = ' ';
    private boolean operatorClicked = false;
    private Label resultDisplay;
    private boolean decimalClicked = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Calculator");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Result display
        resultDisplay = new Label("0");
        resultDisplay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        resultDisplay.setStyle("-fx-font-size: 20;");
        grid.add(resultDisplay, 0, 0, 4, 1);

        // Number buttons
        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            final String digit = Integer.toString(i);
            numberButtons[i] = new Button(digit);
            numberButtons[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            numberButtons[i].setOnAction(e -> handleNumberClick(digit));
        }
        grid.add(numberButtons[7], 0, 1);
        grid.add(numberButtons[8], 1, 1);
        grid.add(numberButtons[9], 2, 1);
        grid.add(numberButtons[4], 0, 2);
        grid.add(numberButtons[5], 1, 2);
        grid.add(numberButtons[6], 2, 2);
        grid.add(numberButtons[1], 0, 3);
        grid.add(numberButtons[2], 1, 3);
        grid.add(numberButtons[3], 2, 3);
        grid.add(numberButtons[0], 0, 4, 2, 1);

        // Decimal button
        Button decimalButton = new Button(".");
        decimalButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        decimalButton.setOnAction(e -> handleDecimalClick());
        grid.add(decimalButton, 2, 4);

        // Operation buttons
        Button addButton = new Button("+");
        addButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addButton.setOnAction(e -> handleOperation('+'));
        
        Button subtractButton = new Button("-");
        subtractButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        subtractButton.setOnAction(e -> handleOperation('-'));
        
        Button multiplyButton = new Button("*");
        multiplyButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        multiplyButton.setOnAction(e -> handleOperation('*'));
        
        Button divideButton = new Button("/");
        divideButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        divideButton.setOnAction(e -> handleOperation('/'));
        
        Button clearButton = new Button("C");
        clearButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        clearButton.setOnAction(e -> clear());
        
        Button equalsButton = new Button("=");
        equalsButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        equalsButton.setOnAction(e -> calculate());
        
        grid.add(addButton, 3, 1);
        grid.add(subtractButton, 3, 2);
        grid.add(multiplyButton, 3, 3);
        grid.add(divideButton, 3, 4);
        grid.add(clearButton, 0, 5, 2, 1);
        grid.add(equalsButton, 2, 5, 2, 1);

        // Scene
        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleNumberClick(String num) {
        if (operatorClicked) {
            currentInput.setLength(0);
            operatorClicked = false;
        }
        if (currentInput.length() < 12) { // Limit input length to avoid overflow
            currentInput.append(num);
            updateDisplay();
        }
    }

    private void handleDecimalClick() {
        if (!decimalClicked) {
            if (operatorClicked) {
                currentInput.setLength(0);
                operatorClicked = false;
            }
            currentInput.append(".");
            decimalClicked = true;
            updateDisplay();
        }
    }

    private void handleOperation(char op) {
        if (currentInput.length() > 0 && !operatorClicked) {
            calculateIntermediateResult();
            currentOperator = op;
            operatorClicked = true;
            decimalClicked = false;
            updateDisplay();
        }
    }

    private void calculateIntermediateResult() {
        if (currentInput.length() > 0) {
            currentNumber = Double.parseDouble(currentInput.toString());
            if (currentOperator != ' ') {
                switch (currentOperator) {
                    case '+':
                        previousNumber += currentNumber;
                        break;
                    case '-':
                        previousNumber -= currentNumber;
                        break;
                    case '*':
                        previousNumber *= currentNumber;
                        break;
                    case '/':
                        if (currentNumber != 0) {
                            previousNumber /= currentNumber;
                        } else {
                            resultDisplay.setText("Error");
                            clear();
                            return;
                        }
                        break;
                }
            } else {
                previousNumber = currentNumber;
            }
            currentInput.setLength(0);
            currentInput.append(previousNumber);
            currentNumber = 0;
        }
    }

    private void calculate() {
        calculateIntermediateResult();
        currentOperator = ' ';
        operatorClicked = false;
        decimalClicked = false;
        updateDisplay();
    }

    private void clear() {
        currentInput.setLength(0);
        previousNumber = 0;
        currentNumber = 0;
        currentOperator = ' ';
        operatorClicked = false;
        decimalClicked = false;
        resultDisplay.setText("0");
    }

    private void updateDisplay() {
        if (currentInput.length() == 0) {
            resultDisplay.setText("0");
        } else {
            resultDisplay.setText(formatNumber(currentInput.toString()));
        }
    }

    private String formatNumber(String number) {
        double num = Double.parseDouble(number);
        if (num == (long) num) {
            return String.format("%d", (long) num);
        } else {
            return String.format("%s", num);
        }
    }
}
