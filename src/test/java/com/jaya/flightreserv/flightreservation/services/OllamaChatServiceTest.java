package com.jaya.flightreserv.flightreservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class OllamaChatServiceTest {
    @Test
    void sendsMessageToOllamaAndReturnsModelReply() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();
        OllamaChatService service = new OllamaChatService(
                builder,
                "http://localhost:11434",
                "llama3.2:3b");

        server.expect(requestTo("http://localhost:11434/api/chat"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(
                        "{\"model\":\"llama3.2:3b\",\"messages\":[{\"role\":\"user\",\"content\":\"Hello\"}],\"stream\":false}"))
                .andRespond(withSuccess(
                        "{\"message\":{\"content\":\"Hi there\"},\"done\":true}",
                        MediaType.APPLICATION_JSON));

        assertEquals("Hi there", service.reply("Hello"));
        server.verify();
    }

    @Test
    void reportsMissingModelConfigurationWithoutCallingOllama() {
        OllamaChatService service = new OllamaChatService(
                RestClient.builder(),
                "http://localhost:11434",
                " ");

        AiProviderUnavailableException exception = assertThrows(
                AiProviderUnavailableException.class,
                () -> service.reply("Hello"));

        assertEquals("The Ollama model is not configured", exception.getMessage());
    }
}
