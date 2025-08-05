package org.bcliu.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.bcliu.config.DeepSeekConfig;
import org.bcliu.dto.ApiRequest;
import org.bcliu.dto.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {
    private final DeepSeekConfig config;
    private final WebClient webClient;

    public String generateText(String prompt) {
        // 构建请求体
        ApiRequest request = ApiRequest.builder()
                .model("deepseek-chat")
                .messages(List.of(
                        new ApiRequest.Message("user", prompt)
                ))
                .temperature(0.7)
                .build();

        // 发送请求并处理响应
        return webClient.post()
                .uri(config.getChatEndpoint())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + config.getApiKey())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .map(res -> res.getChoices().get(0).getMessage().getContent())
                .block();
    }
}
