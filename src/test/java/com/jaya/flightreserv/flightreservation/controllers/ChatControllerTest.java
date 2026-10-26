package com.jaya.flightreserv.flightreservation.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.jaya.flightreserv.flightreservation.services.AiProviderUnavailableException;
import com.jaya.flightreserv.flightreservation.services.ProjectDataContextService;
import com.jaya.flightreserv.flightreservation.services.OllamaChatService;

@WebMvcTest(ChatController.class)
class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OllamaChatService chatService;

    @MockitoBean
    private ProjectDataContextService projectDataContextService;

    @Test
    void returnsProviderReplyForMessage() throws Exception {
        given(projectDataContextService.getContext()).willReturn("{\"flights\":[]}");
        given(chatService.reply("Hello", "{\"flights\":[]}")).willReturn("Hi there");

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value("Hi there"));
    }

    @Test
    void rejectsBlankMessage() throws Exception {
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\" \"}"))
                .andExpect(status().isBadRequest());
        verify(chatService, never()).reply(anyString(), anyString());
    }

    @Test
    void reportsMissingProviderConfiguration() throws Exception {
        given(projectDataContextService.getContext()).willReturn("{\"flights\":[]}");
        given(chatService.reply("Hello", "{\"flights\":[]}"))
                .willThrow(new AiProviderUnavailableException("The Ollama model is not configured"));

        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Hello\"}"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.error").value("The Ollama model is not configured"));
    }
}
