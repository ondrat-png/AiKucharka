package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainWindow extends JFrame {

    private JTextArea ingredientField;
    private JButton generateButton;
    private JButton favoritesButton;
    private FileRecipeStorage storage;

    /**
     * Constructor for MainWindow, initializes the main window of the application with input field for ingredients
     * buttons for generating recipes and viewing favorites.
     * sets up a graphical components
     */
    public MainWindow() {
        this.storage = new FileRecipeStorage();

        this.setTitle("Recipe Generator");
        this.setSize(500, 450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Upper panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("What's cooking today?");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel infoLabel = new JLabel("Enter your ingredients below: 🍅 🥦 🥩");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setForeground(Color.LIGHT_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(infoLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //Center panel
        ingredientField = new JTextArea();
        ingredientField.setFont(new Font("Arial", Font.PLAIN, 15));
        ingredientField.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(ingredientField);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        ingredientField.setMargin(new Insets(10, 10, 10, 10));

        //Bottom panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));

        Dimension buttonSize = new Dimension(180, 25);

        generateButton = new JButton("✨Generate recipe ✨");
        generateButton.setPreferredSize(buttonSize);
        generateButton.setBackground(Color.decode("#E47A34"));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        favoritesButton = new JButton("❤️ Favorites");
        favoritesButton.setPreferredSize(buttonSize);
        favoritesButton.setBackground(Color.decode("#555555"));
        favoritesButton.setForeground(Color.WHITE);
        favoritesButton.setFont(new Font("Arial", Font.BOLD, 14));
        favoritesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


        buttonPanel.add(generateButton);
        buttonPanel.add(favoritesButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        // button to generate recipe
        generateButton.addActionListener(e -> {
            String ingredients = ingredientField.getText();
            // check if the input is valid
            if (ingredients.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter ingredients");
                return;
            }
            generateButton.setEnabled(false);

            ingredients = ingredients.replace("\n", ", ");
            System.out.println("Generating recipe for ingredients: " + ingredients);

            // try to generate the recipe via API and open the result window
            try {
                GroqApiClient apiClient = new GroqApiClient();
                Recipe generatedRecipe = apiClient.generateRecipe(ingredients);
                RecipeWindow recipeWindow = new RecipeWindow(generatedRecipe, storage);

                setupUnlockOnClose(generateButton, recipeWindow);
                recipeWindow.setVisible(true);

            } catch (Exception ex) {
                generateButton.setEnabled(true);
                JOptionPane.showMessageDialog(this,
                        "Error communicating with AI: " + ex.getMessage(),
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // button to open favorites window
        favoritesButton.addActionListener(e -> {
            try {
                favoritesButton.setEnabled(false);
                FavoritesWindow favoritesWindow = new FavoritesWindow(storage.getAllRecipes(), storage);

                setupUnlockOnClose(favoritesButton, favoritesWindow);
                favoritesWindow.setVisible(true);

            } catch (Exception ex) {
                favoritesButton.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Error opening favorites: " + ex.getMessage());            }
        });
    }

    /**
     * Makes the main window visible on the screen.
     */
    public void showMainWindow() {
        this.setVisible(true);
    }

    /**
     * re-enable the button after closing opened window
     * prevents multiple window opened at the same time
     * @param component the specific component
     * @param window the specific window
     */
    public static void setupUnlockOnClose(JComponent component, JFrame window) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                component.setEnabled(true);
            }
        });
    }
}
