package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class OpenAIService {

    private final WebClient webClient;
    @Value("${openai.api.url}")
    private String openAiApiUrl;
    @Value("${openai.api.key}")
    private String openAiApiKey;

    public OpenAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    public String getAnswer(String question) {

        Map<String, Object> requestBody = Map.of(
                "model", "meta-llama/llama-3.3-70b-instruct:free",
                "messages", new Object[]{
                        Map.of(
                                "role", "user",
                                "content", question
                        )
                }
        );

        return webClient.post()
                .uri(openAiApiUrl)
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}


