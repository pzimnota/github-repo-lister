package com.githublisting.github_repo_lister.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {


    @Bean
    public RestClient githubRestClient(
            @Value("${github.token}") String githubToken,
            @Value("${github.api.base-url}") String baseUrl
    ) {
        return RestClient.builder()
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .baseUrl(baseUrl)
                .build();
    }
}
