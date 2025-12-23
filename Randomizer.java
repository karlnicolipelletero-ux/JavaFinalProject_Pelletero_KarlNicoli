package Roulette; // matches your project package

package Roulette; // matches your project package

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Randomizer {

    private JFrame frame;
    private JTextField inputField;
    private JTextArea itemListArea;
    private JButton addButton, spinButton, clearButton;
    private ArrayList<String> items;
    private Random random;

    public Randomizer() {
        items = new ArrayList<>();
        random = new Random();

        frame = new JFrame("Roulette Randomizer");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(5, 5));
        inputField = new JTextField();
        addButton = new JButton("Add Item");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.NORTH);

        itemListArea = new JTextArea();
        itemListArea.setEditable(false);
        itemListArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(itemListArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        spinButton = new JButton("Roulette");
        clearButton = new JButton("Clear All");
        buttonPanel.add(spinButton);
        buttonPanel.add(clearButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addItem());
        spinButton.addActionListener(e -> spinRoulette());
        clearButton.addActionListener(e -> clearAll());

        frame.setVisible(true);
    }

    private void addItem() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            items.add(text);
            itemListArea.append(text + "\n");
            inputField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a text or number!");
        }
    }

    private void spinRoulette() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Add at least one item first!");
            return;
        }

        new Thread(() -> {
            try {
                int cycles = 20 + random.nextInt(10);
                for (int i = 0; i < cycles; i++) {
                    int index = random.nextInt(items.size());
                    itemListArea.setText(items.get(index));
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void clearAll() {
        items.clear();
        itemListArea.setText("");
    }
}

}
