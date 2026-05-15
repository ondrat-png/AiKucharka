package org.example;

import javax.swing.*;
import java.awt.*;

public class RecipeWindow extends JFrame {

    private Recipe currentRecipe;
    private JButton saveButton;

    public RecipeWindow(Recipe recipe) {
        this.currentRecipe = recipe;
        this.setTitle("Your Recipe: " + recipe.getTitle());
        this.setSize(500, 400);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JTextArea recipeTextArea = new JTextArea();
        recipeTextArea.setEditable(false);
        recipeTextArea.setLineWrap(true);
        recipeTextArea.setWrapStyleWord(true);

        String displayText = "TITLE: " + recipe.getTitle() + "\n" +
                "INGREDIENTS:\n" + recipe.getIngredients() + "\n" +
                "INSTRUCTIONS:\n" + recipe.getInstructions();

        recipeTextArea.setText(displayText);

        JScrollPane scrollPane = new JScrollPane(recipeTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        saveButton = new JButton("Save to Favorites");
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        this.add(mainPanel);

        saveButton.addActionListener(e -> {
            System.out.println("Saving recipe: " + currentRecipe.getTitle());
        });

    }
}
