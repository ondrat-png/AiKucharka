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
        this.setSize(500, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Horni panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel titleLabel = new JLabel("❤️ Your Saved Recipes:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        //Center panel
        DefaultListModel<String> recipeListModel = new DefaultListModel<>();
        if(favoriteRecipes != null) {
            for (Recipe recipe : favoriteRecipes) {
                recipeListModel.addElement(recipe.getTitle());
            }
        }

        JList<String> recipeList = new JList<>(recipeListModel);
        recipeList.setFont(new Font("Arial", Font.PLAIN, 15));
        recipeList.setForeground(Color.WHITE);
        recipeList.setToolTipText("Double-click a recipe to view details");

        JScrollPane scrollPane = new JScrollPane(recipeList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //Bottom panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

        Dimension buttonSize = new Dimension(180, 25);

        JButton removeButton = new JButton("🗑️ Remove");
        removeButton.setPreferredSize(buttonSize);
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setBackground(Color.decode("#D9534F"));
        removeButton.setForeground(Color.WHITE);
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton closeButton = new JButton("❌ Close");
        closeButton.setPreferredSize(buttonSize);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(Color.decode("#555555"));
        closeButton.setForeground(Color.WHITE);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(removeButton);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        // add mouse listener to the list to open the recipe details when double-clicked
        recipeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && recipeList.isEnabled()) {
                    int selectedIndex = recipeList.getSelectedIndex();

                    if (selectedIndex >= 0) {
                        recipeList.setEnabled(false);
                        Recipe selectedRecipe = favoriteRecipes.get(selectedIndex);
                        RecipeWindow recipeWindow = new RecipeWindow(selectedRecipe, storage);

                        MainWindow.setupUnlockOnClose(recipeList, recipeWindow);
                        recipeWindow.setVisible(true);
                    }
                }
            }
        });

        // add action listener to the remove button to delete the selected recipe from favorites
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
