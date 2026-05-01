package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GroqApiClient {

    // Tvůj API klíč (tady vlož ten svůj reálný)
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    /**
     * Tato metoda vezme text se surovinami od uživatele, pošle ho do AI
     * a vrátí hotový objekt Recipe.
     */
    public Recipe generateRecipe(String userIngredients) throws Exception {

        // 1. Sestavení instrukcí pro AI
        String prompt = "I have these ingredients: " + userIngredients + ". Write a short recipe.";

        // 2. Příprava JSON požadavku pro Groq (vyžadujeme odpověď čistě v JSON formátu)
        String jsonBody = """
                {
                  "model": "llama-3.3-70b-versatile",
                  "response_format": {"type": "json_object"},
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a professional chef. Respond ONLY in English. Always return a valid JSON object with EXACTLY these keys: 'title' (string), 'ingredients' (array of strings), and 'instructions' (string)."
                    },
                    {
                      "role": "user",
                      "content": "%s"
                    }
                  ]
                }
                """.formatted(prompt); // Tohle nahradí %s v textu výše za náš prompt

        // 3. Vytvoření a odeslání požadavku
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 4. Zpracování odpovědi, pokud byla úspěšná
        if (response.statusCode() == 200) {
            // Louskání JSONu (cesta k obsahu)
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");

            // Text odpovědi (uvnitř je náš vyžádaný JSON s receptem)
            String contentString = message.getString("content");
            JSONObject recipeJson = new JSONObject(contentString);

            // Vytáhnutí jednotlivých částí z JSONu
            String title = recipeJson.getString("title");
            String instructions = recipeJson.getString("instructions");
            JSONArray ingredientsArray = recipeJson.getJSONArray("ingredients");

            // Převod JSON pole na Java ArrayList
            ArrayList<String> parsedIngredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                parsedIngredients.add(ingredientsArray.getString(i));
            }

            // 5. Vytvoření objektu přesně podle tvého konstruktoru!
            Recipe finalRecipe = new Recipe(title, instructions);
            finalRecipe.setIngredients(parsedIngredients); // Přidání surovin přes setter

            return finalRecipe; // Vracíme hotový recept

        } else {
            // Pokud API vrátí chybu (např. špatný klíč)
            throw new Exception("API Error: " + response.statusCode() + " - " + response.body());
        }
    }
}
