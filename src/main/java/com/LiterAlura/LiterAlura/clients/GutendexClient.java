package com.LiterAlura.LiterAlura.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class GutendexClient {

    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public GutendexClient() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode searchBooksByTitle(String title) throws Exception {
        // Codificar el título para que los espacios y caracteres especiales no causen errores
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
        String url = BASE_URL + "?search=" + encodedTitle;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }
}
