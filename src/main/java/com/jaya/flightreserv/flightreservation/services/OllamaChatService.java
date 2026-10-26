package com.jaya.flightreserv.flightreservation.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;

@Service
public class OllamaChatService {
    private static final String CHAT_PATH = "/api/chat";

    private final RestClient restClient;
    private final String model;

    public OllamaChatService(
            RestClient.Builder restClientBuilder,
            @Value("${ai.ollama.base-url:http://localhost:11434}") String baseUrl,
            @Value("${ai.ollama.model:llama3.2:3b}") String model) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
        this.model = model;
    }

    public String reply(String message) {
        if (model == null || model.isBlank()) {
            throw new AiProviderUnavailableException("The Ollama model is not configured");
        }

        JsonNode response = restClient.post()
                .uri(CHAT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "model", model,
                        "messages", List.of(Map.of("role", "user", "content", message)),
                        "stream", false))
                .retrieve()
                .body(JsonNode.class);

        JsonNode content = response == null
                ? null
                : response.path("message").path("content");
        if (content == null || !content.isTextual() || content.asText().isBlank()) {
            throw new IllegalStateException("Ollama returned an invalid chat response");
        }
        return content.asText();
    }
}
