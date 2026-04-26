package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GroqTest {
    public static void main(String[] args) {
        // 1. Tvůj tajný API klíč z GroqCloud
        String apiKey = "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";

        // 2. Adresa (endpoint), kam posíláme dotaz
        String url = "https://api.groq.com/openai/v1/chat/completions";

        // 3. Tělo dotazu - Zde definujeme model a naši otázku.
        // (Používám textový blok """, který funguje v moderní Javě, ať se to dobře čte)
        String jsonBody = """
                {
                  "model": "llama-3.3-70b-versatile",
                  "messages": [
                    {
                      "role": "system",
                      "content": "Jsi profesionální kuchař. Odpovídej v češtině."
                    },
                    {
                      "role": "user",
                      "content": "Mám doma brambory, vejce a mouku. Napiš mi krátký recept, co z toho uvařit."
                    }
                  ]
                }
                """;

        try {
            // 4. Vytvoření "pošťáka" (HttpClient), který dotaz doručí
            HttpClient client = HttpClient.newHttpClient();

            // 5. Zabalení balíčku (kam letí, jaký má klíč a co obsahuje)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + apiKey) // Tímto dokazujeme, že jsme to my
                    .header("Content-Type", "application/json")  // Říkáme, že posíláme JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            System.out.println("Odesílám dotaz do Groq API... Čekej...\n");

            // 6. Odeslání a čekání na odpověď
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 7. Vypsání toho, co nám server vrátil
            System.out.println("Status kód (200 znamená OK): " + response.statusCode());
            System.out.println("--- Odpověď od AI ---");
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println("Něco se pokazilo: " + e.getMessage());
        }
    }
}