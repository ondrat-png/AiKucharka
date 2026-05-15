package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FavoritesWindow  extends JFrame {

    public  FavoritesWindow(ArrayList<Recipe> favoriteRecipes) {
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

        closeButton.addActionListener(e -> {
            this.dispose();
        });
    }

}
