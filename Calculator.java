package app;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {

    int boardWidth = 360;
    int boardHeight = 720;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    // --- Button Layout, neatly arranged 8x4 ---
    String[] buttonValues = {
        "AC", "DEL", "+/-", "%",
        "sin", "cos", "tan", "√",
        "asin", "acos", "atan", "÷",
        "7", "8", "9", "×", 
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", "ANS", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "DEL", "+/-", "%", "√"};
    String[] trigFunctions = {"sin", "cos", "tan"};
    String[] invTrigFunctions = {"asin", "acos", "atan"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;
    String ans = "0"; // Store last answer

    public Calculator() {

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- Display ---
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // --- Buttons Layout ---
        buttonsPanel.setLayout(new GridLayout(8, 4)); // 8 rows, 4 columns
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (String value : buttonValues) {
            JButton button = new JButton(value);
            styleButton(button);
            buttonsPanel.add(button);
            button.addActionListener(e -> handleButtonClick(button.getText()));
        }

        frame.setVisible(true);
    }

    // ----------------------------
    void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 22));
        button.setFocusable(false);
        button.setBorder(new LineBorder(customBlack));

        String v = button.getText();

        if (Arrays.asList(topSymbols).contains(v)) {
            button.setBackground(customLightGray);
            button.setForeground(Color.black);
        } 
        else if (Arrays.asList(rightSymbols).contains(v)) {
            button.setBackground(customOrange);
            button.setForeground(Color.white);
        } 
        else if (Arrays.asList(trigFunctions).contains(v)) {
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.white);
        }
        else if (Arrays.asList(invTrigFunctions).contains(v)) {
            button.setBackground(new Color(90, 90, 160));
            button.setForeground(Color.white);
        }
        else if (v.equals("ANS")) {
            button.setBackground(new Color(34, 139, 34)); // green
            button.setForeground(Color.white);
        }
        else {
            button.setBackground(customDarkGray);
            button.setForeground(Color.white);
        }
    }

    // ----------------------------
    void handleButtonClick(String value) {

        if (Arrays.asList(rightSymbols).contains(value)) {
            handleOperators(value);
            return;
        }
        if (Arrays.asList(topSymbols).contains(value)) {
            handleTop(value);
            return;
        }
        if (Arrays.asList(trigFunctions).contains(value)) {
            handleTrig(value);
            return;
        }
        if (Arrays.asList(invTrigFunctions).contains(value)) {
            handleInverseTrig(value);
            return;
        }
        if (value.equals("ANS")) {
            displayLabel.setText(ans);
            return;
        }

        handleNumbers(value);
    }

    // ----------------------------
    void handleNumbers(String value) {
        if (value.equals(".")) {
            if (!displayLabel.getText().contains(".")) {
                displayLabel.setText(displayLabel.getText() + ".");
            }
            return;
        }
        if (displayLabel.getText().equals("0")) {
            displayLabel.setText(value);
        } else {
            displayLabel.setText(displayLabel.getText() + value);
        }
    }

    // ----------------------------
    void handleTop(String v) {
        switch (v) {
            case "AC":
                clearAll();
                displayLabel.setText("0");
                break;
            case "DEL":
                String text = displayLabel.getText();
                if (text.length() > 1) {
                    displayLabel.setText(text.substring(0, text.length() - 1));
                } else {
                    displayLabel.setText("0");
                }
                break;
            case "+/-":
                double n = Double.parseDouble(displayLabel.getText());
                displayLabel.setText(removeZeroDecimal(n * -1));
                break;
            case "%":
                double percent = Double.parseDouble(displayLabel.getText()) / 100;
                displayLabel.setText(removeZeroDecimal(percent));
                break;
            case "√":
                double sqrtVal = Math.sqrt(Double.parseDouble(displayLabel.getText()));
                displayLabel.setText(removeZeroDecimal(sqrtVal));
                break;
        }
    }

    // ----------------------------
    void handleTrig(String f) {
        double rad = Math.toRadians(Double.parseDouble(displayLabel.getText()));
        double result = 0;
        switch (f) {
            case "sin": result = Math.sin(rad); break;
            case "cos": result = Math.cos(rad); break;
            case "tan": result = Math.tan(rad); break;
        }
        displayLabel.setText(removeZeroDecimal(result));
    }

    // ----------------------------
    void handleInverseTrig(String f) {
        double val = Double.parseDouble(displayLabel.getText());
        double result = 0;

        switch (f) {
            case "asin":
                if (val < -1 || val > 1) {
                    JOptionPane.showMessageDialog(frame, "asin input must be between -1 and 1");
                    return;
                }
                result = Math.toDegrees(Math.asin(val));
                break;
            case "acos":
                if (val < -1 || val > 1) {
                    JOptionPane.showMessageDialog(frame, "acos input must be between -1 and 1");
                    return;
                }
                result = Math.toDegrees(Math.acos(val));
                break;
            case "atan":
                result = Math.toDegrees(Math.atan(val));
                break;
        }

        displayLabel.setText(removeZeroDecimal(result));
    }

    // ----------------------------
    void handleOperators(String op) {
        if (op.equals("=")) {
            if (operator != null) {
                B = displayLabel.getText();
                double a = Double.parseDouble(A);
                double b = Double.parseDouble(B);
                double result = 0;
                switch (operator) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "×": result = a * b; break;
                    case "÷": result = a / b; break;
                }
                displayLabel.setText(removeZeroDecimal(result));
                ans = removeZeroDecimal(result); // store last answer
                clearAll();
            }
            return;
        }

        if (operator == null) {
            A = displayLabel.getText();
            displayLabel.setText("	");
        }
        operator = op;
    }

    // ----------------------------
    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double num) {
        if (num % 1 == 0)
            return Integer.toString((int) num);
        return Double.toString(num);
    }
}
