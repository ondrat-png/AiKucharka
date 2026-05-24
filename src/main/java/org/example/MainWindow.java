package org.example;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    private JTextArea ingredientField;
    private JButton generateButton;
    private JButton favoritesButton;
    private FileRecipeStorage storage;

    public MainWindow() {
        this.storage = new FileRecipeStorage();

        this.setTitle("Recipe Generator");
        this.setSize(400, 300);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel infoLabel = new JLabel("Enter ingredients:");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
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
                RecipeWindow recipeWindow = new RecipeWindow(generatedRecipe, storage);
                recipeWindow.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error communicating with AI: " + ex.getMessage(),
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        favoritesButton.addActionListener(e -> {
            try {
                FavoritesWindow favoritesWindow = new FavoritesWindow(storage.getAllRecipes(), storage);
                favoritesWindow.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening favorites: " + ex.getMessage());            }
        });
    }

    public void showMainWindow() {
        this.setVisible(true);
    }
}
