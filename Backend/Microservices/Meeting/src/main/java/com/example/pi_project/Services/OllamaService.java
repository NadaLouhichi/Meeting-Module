package com.example.pi_project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
@Service
public class OllamaService {

    @Value("${ollama.api.url:http://localhost:11434/api/generate}")
    private String ollamaApiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public OllamaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String askMistral(String prompt) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Configure Mistral-specific parameters
            String requestBody = String.format(
                    "{" +
                            "\"model\": \"mistral\"," +
                            "\"prompt\": \"%s\"," +
                            "\"stream\": false," +
                            "\"options\": {" +
                            "\"temperature\": 0.7," +       // Control creativity (0=strict, 1=creative)
                            "\"top_p\": 0.9," +             // Nucleus sampling
                            "\"max_tokens\": 150," +        // Response length limit
                            "\"repeat_penalty\": 1.1" +     // Reduce repetition
                            "}" +
                            "}",
                    prompt.replace("\"", "\\\"")  // Escape quotes
            );

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    ollamaApiUrl,
                    request,
                    String.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to call Mistral via Ollama: " + e.getMessage(), e);
        }
    }
}