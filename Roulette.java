package Roulette; // matches your project package

import javax.swing.*;

public class Roulette {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Randomizer randomizer = new Randomizer();
        });
    }
}
