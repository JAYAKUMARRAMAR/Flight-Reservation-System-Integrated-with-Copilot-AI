package com.jaya.flightreserv.flightreservation.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestClientException;

import com.jaya.flightreserv.flightreservation.dtos.ChatRequest;
import com.jaya.flightreserv.flightreservation.dtos.ChatResponse;
import com.jaya.flightreserv.flightreservation.services.AiProviderUnavailableException;
import com.jaya.flightreserv.flightreservation.services.ProjectDataContextService;
import com.jaya.flightreserv.flightreservation.services.OllamaChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {
    private final OllamaChatService chatService;
    private final ProjectDataContextService projectDataContextService;

    public ChatController(
            OllamaChatService chatService,
            ProjectDataContextService projectDataContextService) {
        this.chatService = chatService;
        this.projectDataContextService = projectDataContextService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody(required = false) ChatRequest request) {
        if (request == null || request.message() == null || request.message().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        String projectDataContext = projectDataContextService.getContext();
        return ResponseEntity.ok(new ChatResponse(chatService.reply(request.message(), projectDataContext)));
    }

    @ExceptionHandler(AiProviderUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleProviderUnavailable(AiProviderUnavailableException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler({RestClientException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, String>> handleProviderFailure(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", exception.getMessage()));
    }
}
