package tech.lastbox.structo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.lastbox.structo.dtos.PromptRequest;

import java.util.*;


@Service
@ComponentScan(basePackages = "org.springframework.web.client.RestTemplate;")
public class OllamaService {

    @Value("${ollama.model}")
    private String model;

    @Value("${ollama.url}")
    private String OLLAMA_BASE_URL;

    private final RestTemplate restTemplate;

    public OllamaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public ResponseEntity<?> sendPrompt(String promptDescription) {
        try {
            PromptRequest request = new PromptRequest(
                    model,
                    promptDescription,
                    "json",
                    false);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    OLLAMA_BASE_URL,
                    request,
                    String.class);

            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));

        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


