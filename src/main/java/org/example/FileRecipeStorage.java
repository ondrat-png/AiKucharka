package org.example;

import java.util.ArrayList;

public class FileRecipeStorage implements RecipeStorage {

    private ArrayList<Recipe> saveRecipes;

    public  FileRecipeStorage() {
        this.saveRecipes = new ArrayList<>();
    }

    @Override
    public void saveRecipe(Recipe recipe) throws Exception {
        if(saveRecipes == null){
            throw  new Exception("Recipe can not be null");
        }
        saveRecipes.add(recipe);
        System.out.println("Recipe saved: " + recipe.getTitle());
    }

    @Override
    public ArrayList<Recipe> getAllRecipes() throws Exception {
        return saveRecipes;
    }
}
