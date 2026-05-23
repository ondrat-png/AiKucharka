package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FavoritesWindow  extends JFrame {

    private ArrayList<Recipe> favoriteRecipes;
    private FileRecipeStorage storage;

    public  FavoritesWindow(ArrayList<Recipe> favoriteRecipes, FileRecipeStorage storage) {
        this.favoriteRecipes = favoriteRecipes;
        this.storage = storage;

        this.setTitle("Favorite recipes");
        this.setSize(400, 300);

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

        JButton closeButton = new JButton("Close");
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        this.add(mainPanel);

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

        closeButton.addActionListener(e -> {
            this.dispose();
        });
    }

}
