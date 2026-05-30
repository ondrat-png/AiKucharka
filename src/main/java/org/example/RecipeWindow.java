package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.net.URL;

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
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Upper panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout(0,10));

        JLabel titleLabel = new JLabel(" 🍽️ " + recipe.getTitle().toUpperCase());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.NORTH);


        JLabel imageLabel = new JLabel("Loading image... ⏳", SwingConstants.CENTER);
        imageLabel.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(imageLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        new Thread(() -> {
            try {
                String imageUrl = UnsplashApiClient.getImageUrl(recipe.getTitle());
                ImageIcon imageIcon = new ImageIcon(new URL(imageUrl));
                Image image = imageIcon.getImage().getScaledInstance(450, 250, Image.SCALE_SMOOTH);

                SwingUtilities.invokeLater(() -> {
                    imageLabel.setText("");
                    imageLabel.setIcon(new ImageIcon(image));
                });
            } catch (Exception e) {
                imageLabel.setText("Image not available ❌");
            }
        }).start();

        //Center panel
        JTextArea recipeTextArea = new JTextArea();
        recipeTextArea.setEditable(false);
        recipeTextArea.setLineWrap(true);
        recipeTextArea.setWrapStyleWord(true);
        recipeTextArea.setFont(new Font("Arial", Font.PLAIN, 15));
        recipeTextArea.setMargin(new Insets(15, 15, 15, 15));
        recipeTextArea.setForeground(Color.WHITE);

        String displayText =
                "🛒 INGREDIENTS\n" +
                        "--------------------------------------------------\n" +
                        formatIngredients(recipe.getIngredients()) + "\n\n" +

                        "📖 INSTRUCTIONS\n" +
                        "--------------------------------------------------\n" +
                        recipe.getInstructions();

        recipeTextArea.setText(displayText);

        JScrollPane scrollPane = new JScrollPane(recipeTextArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //Bottom panel
        JPanel buttonPanel = new JPanel();

        saveButton = new JButton("❤️ Save to Favorites");
        saveButton.setPreferredSize(new Dimension(180, 25));
        saveButton.setBackground(Color.decode("#E47A34"));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        // button to save the recipe to favorites
        saveButton.addActionListener(e -> {
            try {
                this.storage.saveRecipe(currentRecipe);
                JOptionPane.showMessageDialog(this, "Recipe saved to favorites!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // disable the button after succesful save to prevent duplicate saves
                saveButton.setEnabled(false);
                saveButton.setText("✅ Saved");
                saveButton.setBackground(Color.decode("#555555"));
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
            result += "- " + ingredient + "\n";
        }
        return result;
    }
}

