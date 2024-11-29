package tech.lastbox.structo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    }

    public Optional<String> sendPrompt(String promptDescription) {
        PromptRequest request = new PromptRequest(
                model,
                promptDescription,
                "json",
                false
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                OLLAMA_BASE_URL,
                request,
                String.class
        );

        ObjectMapper responseMapper = new ObjectMapper();

        try {
            JsonNode jsonResponse = responseMapper.readTree(response.getBody());

            return Optional.of(jsonResponse.get("response").asText().replace("\\\"", "\""));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}


