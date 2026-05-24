package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RecipeWindow extends JFrame {

    private Recipe currentRecipe;
    private JButton saveButton;
    private FileRecipeStorage storage;

    /**
     * Constructs the recipe window, displaying the details of the generated recipe.
     * @param recipe the reecipe to be displayed
     * @param storage is used to store the saved recipe
     */
    public RecipeWindow(Recipe recipe, FileRecipeStorage storage) {
        this.currentRecipe = recipe;
        this.storage = storage;

        this.setTitle("Your Recipe: " + recipe.getTitle());
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JTextArea recipeTextArea = new JTextArea();
        recipeTextArea.setEditable(false);
        recipeTextArea.setLineWrap(true);
        recipeTextArea.setWrapStyleWord(true);

        String displayText = "TITLE: " + recipe.getTitle() + "\n" +
                "INGREDIENTS:\n" + formatIngredients(recipe.getIngredients()) + "\n" +
                "INSTRUCTIONS:\n" + recipe.getInstructions();

        recipeTextArea.setText(displayText);

        JScrollPane scrollPane = new JScrollPane(recipeTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        saveButton = new JButton("Save to Favorites");
        mainPanel.add(saveButton, BorderLayout.SOUTH);

        this.add(mainPanel);

        // button to save the recipe to favorites
        saveButton.addActionListener(e -> {
            try {
                this.storage.saveRecipe(currentRecipe);
                JOptionPane.showMessageDialog(this, "Recipe saved to favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // disable the button after succesful save to prevent duplicate saves
                saveButton.setEnabled(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            }
        });

    }

    /**
     * formats the list of ingredients
     * @param ingredients the list of ingredients
     * @return a format string witch ingredients on each line
     */
    private String formatIngredients(ArrayList<String> ingredients) {
        String result = "";
        for (String ingredient : ingredients) {
            result += ingredient + "\n";
        }
        return result;
    }
}

