package org.example;
import java.util.ArrayList;

public class Recipe {

    private String title;
    private ArrayList<String> ingredients;
    private String instructions;

    public Recipe(String title, String instructions) {
        this.title = title;
        this.ingredients = new ArrayList<>();
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
