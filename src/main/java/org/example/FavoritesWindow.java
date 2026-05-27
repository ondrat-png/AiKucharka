package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FavoritesWindow  extends JFrame {

    private ArrayList<Recipe> favoriteRecipes;
    private FileRecipeStorage storage;

    /**
     * Constructs the favorites window and populates the list with saved recipes.
     * @param favoriteRecipes the list of recipes to be displayed in the window
     * @param storage local storage system
     */
    public  FavoritesWindow(ArrayList<Recipe> favoriteRecipes, FileRecipeStorage storage) {
        this.favoriteRecipes = favoriteRecipes;
        this.storage = storage;

        this.setTitle("Favorite recipes");
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Your Saved Recipes:");
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> recipeListModel = new DefaultListModel<>();
        if(favoriteRecipes != null) {
            for (Recipe recipe : favoriteRecipes) {
                recipeListModel.addElement(recipe.getTitle());
            }
        }

        JList<String> recipeList = new JList<>(recipeListModel);

        JScrollPane scrollPane = new JScrollPane(recipeList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Remove");
        JButton closeButton = new JButton("Close");

        buttonPanel.add(removeButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        // add mouse listener to the list to open the recipe details when double-clicked
        recipeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = recipeList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        Recipe selectedRecipe = favoriteRecipes.get(selectedIndex);
                        RecipeWindow recipeWindow = new RecipeWindow(selectedRecipe, storage);
                        recipeWindow.setVisible(true);
                    }
                }
            }
        });

        /**
         * action listener to remove from favorite
         * it checks if a recipe is selected
         * asks for confirmation before deleting the recipe
         */
        removeButton.addActionListener(e -> {
            int selectedIndex = recipeList.getSelectedIndex();

            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(this, "Please select a recipe to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Recipe selectedRecipe = favoriteRecipes.get(selectedIndex);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete '" + selectedRecipe.getTitle() + "'?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    storage.deleteRecipe(selectedRecipe);
                    recipeListModel.remove(selectedIndex);
                    JOptionPane.showMessageDialog(this, "Recipe removed from favorites.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting recipe: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });



        //action listener to close the window
        closeButton.addActionListener(e -> {
            this.dispose();
        });
    }

}
