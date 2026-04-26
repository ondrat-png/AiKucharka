package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class GroqTest {
    public static void main(String[] args) {
        String apiKey = "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";
        String url = "https://api.groq.com/openai/v1/chat/completions";

        String jsonBody = """
                {
                  "model": "llama-3.3-70b-versatile",
                  "response_format": {"type": "json_object"},
                  "messages": [
                    {
                      "role": "system",
                      "content": "You are a professional chef. You must respond ONLY in English. Always return the response in a valid JSON format with the following keys: 'title' (string), 'ingredients' (array of strings), and 'instructions' (string)."
                    },
                    {
                      "role": "user",
                      "content": "I have potatoes, eggs, and flour. Write a short recipe."
                    }
                  ]
                }
                """;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            System.out.println("Odesílám dotaz do Groq API... Čekej...\n");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // --- NOVÁ ČÁST PRO ZPRACOVÁNÍ JSONU ---
            if (response.statusCode() == 200) {
                // 1. Převedeme ten obří text na JSON Objekt
                JSONObject jsonResponse = new JSONObject(response.body());

                // 2. Najdeme to pole s názvem "choices"
                JSONArray choices = jsonResponse.getJSONArray("choices");

                // 3. Vezmeme z něj první výsledek (index 0) a najdeme v něm "message"
                JSONObject message = choices.getJSONObject(0).getJSONObject("message");

                // 4. Z "message" vytáhneme jen samotný text pod názvem "content"
                String cistyRecept = message.getString("content");

                // 5. Vypíšeme to uživateli
                System.out.println("================ VÝSLEDNÝ RECEPT ================");
                System.out.println(cistyRecept);
                System.out.println("=================================================");
            } else {
                System.out.println("Chyba při komunikaci: " + response.statusCode());
                System.out.println(response.body());
            }

        } catch (Exception e) {
            System.out.println("Něco se pokazilo: " + e.getMessage());
        }
    }
}