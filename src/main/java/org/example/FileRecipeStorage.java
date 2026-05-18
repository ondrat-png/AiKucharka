package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
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


    }
}
