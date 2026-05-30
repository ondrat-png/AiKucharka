package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GroqApiClient {
    private static final String API_KEY = System.getenv("GROQ_API_KEY");
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    /**
     * get the users ingredients and send it to AI
     * @param userIngredients users ingredints
     */
    //AI
    public Recipe generateRecipe(String userIngredients) throws Exception {
        // 1. instructions for the AI
        String prompt = "I have these ingredients: " + userIngredients + ". Write a short recipe.";
        String jsonBody = """
                {
                  "model": "llama-3.3-70b-versatile",
                  "response_format": {"type": "json_object"},
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a professional chef. Respond ONLY in English. Always return a valid JSON object with EXACTLY these keys: 'title' (string), 'ingredients' (array of strings with quantities like '200g flour', '500ml water'), and 'instructions' (string)."                             },
                    {
                      "role": "user",
                      "content": "%s"
                    }
                  ]
                }
                """.formatted(prompt);
        // 3. created HTTP client and request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // 4. processing answer
        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");

            // answer valid json
            String contentString = message.getString("content");
            JSONObject recipeJson = new JSONObject(contentString);

            String title = recipeJson.getString("title");
            String instructions = recipeJson.getString("instructions");
            JSONArray ingredientsArray = recipeJson.getJSONArray("ingredients");

            ArrayList<String> parsedIngredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                parsedIngredients.add(ingredientsArray.getString(i));
            }
            // 5. final recipe
            Recipe finalRecipe = new Recipe(title, instructions);
            finalRecipe.setIngredients(parsedIngredients);
            return finalRecipe;
        } else {
            throw new Exception("API Error: " + response.statusCode() + " - " + response.body());
        }
    }
}
