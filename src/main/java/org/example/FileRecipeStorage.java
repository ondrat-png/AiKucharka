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

    @Override
    public void saveRecipe(Recipe recipe) throws Exception {
        if(recipe == null){
            throw  new Exception("Recipe can not be null");
        }
        saveRecipes.add(recipe);
        System.out.println("Recipe saved: " + recipe.getTitle());
        saveRecipesToFile();
    }

    @Override
    public ArrayList<Recipe> getAllRecipes() throws Exception {
        return saveRecipes;
    }


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
}
