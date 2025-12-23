package Roulette;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Randomizer {

    private JFrame frame;

    // Item input
    private JTextField itemField;
    private JButton addItemButton;

    // Display
    private JTextArea itemListArea;
    private JButton spinButton, clearButton;

    private ArrayList<String> items;
    private Random random;

    public Randomizer() {
        items = new ArrayList<>();
        random = new Random();

        // -------- Main frame --------
        frame = new JFrame("Roulette Randomizer");
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.BLACK);

        // -------- Item Input Panel --------
        JPanel itemPanel = new JPanel(new BorderLayout(5, 5));
        itemPanel.setBackground(Color.BLACK);
        itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel itemLabel = new JLabel("Enter item for roulette:");
        itemLabel.setForeground(Color.WHITE);
        itemLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        itemField = new JTextField();
        itemField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        itemField.setBackground(Color.BLACK);
        itemField.setForeground(Color.WHITE);
        itemField.setCaretColor(Color.WHITE);

        addItemButton = new JButton("Add Item");
        addItemButton.setBackground(new Color(0, 102, 204));
        addItemButton.setForeground(Color.WHITE);
        addItemButton.setFocusPainted(false);

        JPanel itemInputPanel = new JPanel(new BorderLayout(5, 5));
        itemInputPanel.setBackground(Color.BLACK);
        itemInputPanel.add(itemField, BorderLayout.CENTER);
        itemInputPanel.add(addItemButton, BorderLayout.EAST);

        itemPanel.add(itemLabel, BorderLayout.NORTH);
        itemPanel.add(itemInputPanel, BorderLayout.CENTER);

        frame.add(itemPanel, BorderLayout.NORTH);

        // -------- List Display --------
        itemListArea = new JTextArea();
        itemListArea.setEditable(false);
        itemListArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        itemListArea.setBackground(Color.BLACK);
        itemListArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(itemListArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // -------- Buttons Panel --------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        spinButton = new JButton("Roulette");
        spinButton.setBackground(new Color(0, 102, 204));
        spinButton.setForeground(Color.WHITE);
        spinButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        spinButton.setFocusPainted(false);

        clearButton = new JButton("Clear All");
        clearButton.setBackground(new Color(0, 102, 204));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        clearButton.setFocusPainted(false);

        buttonPanel.add(spinButton);
        buttonPanel.add(clearButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // -------- Event Listeners --------
        addItemButton.addActionListener(e -> addItemToList());
        spinButton.addActionListener(e -> spinRoulette());
        clearButton.addActionListener(e -> clearAll());

        frame.setVisible(true);
    }

    // Adds an item to roulette
    private void addItemToList() {
        String text = itemField.getText().trim();
        if (!text.isEmpty()) {
            items.add(text);
            itemListArea.append(text + "\n");
            itemField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a text or number!");
        }
    }

    // Spins roulette with true shuffle and keeps previous items visible
    private void spinRoulette() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Add at least one item first!");
            return;
        }

        new Thread(() -> {
            try {
                ArrayList<String> spinList = new ArrayList<>(items);
                int cycles = 5; // Number of shuffle cycles for animation

                for (int i = 0; i < cycles; i++) {
                    java.util.Collections.shuffle(spinList, random);
                    for (String item : spinList) {
                        SwingUtilities.invokeLater(() ->
                            itemListArea.append("→ " + item + "\n")
                        );
                        Thread.sleep(100);
                    }
                }

                // Final selected item
                String finalItem = items.get(random.nextInt(items.size()));
                SwingUtilities.invokeLater(() -> {
                    itemListArea.append("\n🎯 Result: " + finalItem + " 🎯\n\n");
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Clears everything
    private void clearAll() {
        items.clear();
        itemListArea.setText("");
        itemField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Randomizer::new);
    }
}

