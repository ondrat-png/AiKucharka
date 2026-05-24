package org.example;

import java.util.ArrayList;

public interface RecipeStorage {

    void saveRecipe(Recipe recipe) throws Exception;

    ArrayList<Recipe> getAllRecipes();

}
