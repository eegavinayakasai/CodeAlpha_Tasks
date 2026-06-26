package com.evs.chatbot.service;

import com.evs.chatbot.model.ChatMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Service
public class Aiservice {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getResponse(List<ChatMessage> history) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        try {

            List<Map<String, Object>> contents = history.stream()
                    .map(message -> Map.of(
                            "role", "bot".equals(message.role()) ? "model" : "user",
                            "parts", List.of(Map.of("text", message.text()))
                    ))
                    .toList();

            Map<String, Object> requestBody = Map.of("contents", contents);
            String jsonRequest = mapper.writeValueAsString(requestBody);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);


            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = mapper.readTree(response.getBody());

                // Navigate the JSON tree: candidates[0] -> content -> parts[0] -> text
                String aiMessage = root.path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text")
                        .asText();

                System.out.println("AI Response: " + aiMessage);
                return aiMessage;
            } else {
                return "Error: Received status code " + response.getStatusCode();
            }

        }
        catch (org.springframework.web.client.HttpClientErrorException.TooManyRequests e) {
            return "Slow down! You've hit the free tier limit. Try again in a minute.";
        }
        catch (Exception e) {
            return "Failed to get AI response, check you connection";
        }
    }
}
