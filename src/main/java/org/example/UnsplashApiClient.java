package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UnsplashApiClient {
    private static final String ACCESS_KEY = System.getenv("UNSPLAYSH_API_KEY");

    /**
     * try to search picture by URL
     * @param recipeName the search query
     */
    //AI
    public static String getImageUrl(String recipeName) {
        try {
            String searchQuery = recipeName + " food";
            // Convert spaces in the name to URL format
            String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8.toString());
            String urlString = "https://api.unsplash.com/search/photos?page=1&per_page=1&query="
                    + encodedQuery + "&client_id=" + ACCESS_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();
            // Quick trick: Extract image URL from JSON without additional libraries
            String json = content.toString();
            String target = "\"regular\":\"";
            int startIndex = json.indexOf(target);
            if (startIndex != -1) {
                startIndex += target.length();
                int endIndex = json.indexOf("\"", startIndex);
                return json.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            System.out.println("Error fetching image from Unsplash: " + e.getMessage());
        }
        return "https://img.freepik.com/free-photo/fresh-gourmet-meal-beef-taco-salad-generated-by-ai_188544-13381.jpg";
    }
}