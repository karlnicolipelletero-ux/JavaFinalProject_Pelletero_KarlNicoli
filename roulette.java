package Roulette; // match the package in your IDE

import javax.swing.*;

public class roulette {
    public static void main(String[] args) {
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new Randomizer(); // create Randomizer instance
        });
    }
}
