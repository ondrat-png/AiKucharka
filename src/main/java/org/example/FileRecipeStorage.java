package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class FileRecipeStorage implements RecipeStorage {

    private ArrayList<Recipe> saveRecipes;
    private static final String FILE_NAME = "favorites.json";


    public  FileRecipeStorage() {
        this.saveRecipes = new ArrayList<>();
        loadRecipeFromFile();
    }

    /**
     * save recipe to arraylist and then save to file
     * prevents from saving duplicates
     * @param recipe the object to be saved
     * @throws Exception if it is null or if there is already a recipe with the same title
     */
    @Override
    public void saveRecipe(Recipe recipe) throws Exception {
        if(recipe == null){
            throw  new Exception("Recipe can not be null");
        }

        for (Recipe existingRecipe : saveRecipes) {
            if (existingRecipe.getTitle().equalsIgnoreCase(recipe.getTitle())) {
                throw new Exception("Recipe with the same title already exists.");
            }
        }
        saveRecipes.add(recipe);
        System.out.println("Recipe saved: " + recipe.getTitle());
        saveRecipesToFile();
    }

    /**
     * @return arraylist of saved recipe
     */
    @Override
    public ArrayList<Recipe> getAllRecipes(){
        return saveRecipes;
    }


    /**
     * Converts the internal list of recipes into a JSON array and writes it to the local file.
     * Formats the output with an indentation of 4 spaces for better readability.
     */
    private void saveRecipesToFile() {
        try {
            JSONArray jsonArray = new JSONArray();

            for (Recipe recipe : saveRecipes) {
                JSONObject jsonRecipe = new JSONObject();
                jsonRecipe.put("title", recipe.getTitle());
                jsonRecipe.put("ingredients", new JSONArray(recipe.getIngredients()));
                jsonRecipe.put("instructions", recipe.getInstructions());

                jsonArray.put(jsonRecipe);
            }

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        writer.write(jsonArray.toString(4));
        writer.close();

        } catch (Exception e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }


    /**
     * read the file and convert the content to json array
     * get the information about the recipe
     * created new recipe
     * catch error if file not found or loading
     */
    public void  loadRecipeFromFile(){
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String content = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content = content + line;
            }
            bufferedReader.close();

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonRecipe = jsonArray.getJSONObject(i);

                String title = jsonRecipe.getString("title");
                String instructions = jsonRecipe.getString("instructions");
                JSONArray ingredientsArray = jsonRecipe.getJSONArray("ingredients");

                ArrayList<String> ingredients = new ArrayList<>();
                for (Object item : ingredientsArray.toList()) {
                    ingredients.add(item.toString());
                }

                Recipe recipe = new Recipe(title, instructions);
                recipe.setIngredients(ingredients);
                saveRecipes.add(recipe);
            }

            System.out.println("Recipes loaded: " + saveRecipes.size());

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading recipes: " + e.getMessage());
        }
    }

    public void deleteRecipe(Recipe recipe){
        if (recipe != null){
            saveRecipes.remove(recipe);
            saveRecipesToFile();
            System.out.println("Recipe deleted: " + recipe.getTitle());
        }
    }
}
