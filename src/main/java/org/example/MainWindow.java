package org.example;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    private JTextArea ingredientField;
    private JButton generateButton;
    private JButton favoritesButton;

    public MainWindow() {
        this.setTitle("Recipe Generator");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel infoLabel = new JLabel("Enter ingredients:");
        mainPanel.add(infoLabel, BorderLayout.NORTH);

        ingredientField = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(ingredientField);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        generateButton = new JButton("Generate recipe");
        favoritesButton = new JButton("Favorites");

        buttonPanel.add(generateButton);
        buttonPanel.add(favoritesButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        generateButton.addActionListener(e -> {
            String ingredients = ingredientField.getText();

            if (ingredients.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter ingredients");
                return;
            }

            ingredients = ingredients.replace("\n", ", ");
            System.out.println("Generating recipe for ingredients: " + ingredients);

            try {
                GroqApiClient apiClient = new GroqApiClient();
                Recipe generatedRecipe = apiClient.generateRecipe(ingredients);
                RecipeWindow recipeWindow = new RecipeWindow(generatedRecipe);
                recipeWindow.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }


        });

        favoritesButton.addActionListener(e -> {
            System.out.println("Opening favorites");
        });
    }

    public  static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }

}
