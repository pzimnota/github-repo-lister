package com.githublisting.github_repo_lister.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${github.token:}")
    private String githubToken;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        if (githubToken != null && !githubToken.isEmpty()) {
            restTemplate.getInterceptors().add((request, body, execution) -> {
                request.getHeaders().add("Authorization", "Bearer " + githubToken);
                return execution.execute(request, body);
            });
        }

        return restTemplate;
    }
}

