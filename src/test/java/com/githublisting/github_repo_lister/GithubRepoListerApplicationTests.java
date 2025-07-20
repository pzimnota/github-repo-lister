package com.githublisting.github_repo_lister;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubRepoListerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenValidUser_whenCallingApi_thenReturnCorrectRepositoryData() {
        // given
        String username = "octocat";
        String url = "/repositories/" + username;

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // then
		String body = response.getBody();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode root = objectMapper.readTree(body);

			JsonNode firstRepo = root.get(0);
			assertThat(firstRepo.get("Repository Name").asText()).isEqualTo("Hello-World");
			assertThat(firstRepo.get("Owner Login").get("login").asText()).isNotBlank();
			JsonNode branches = firstRepo.get("branches");
			assertThat(branches).isNotNull();
			assertThat(branches.isArray()).isTrue();
			assertThat(branches.size()).isGreaterThan(0);
			JsonNode firstBranch = branches.get(0);
			assertThat(firstBranch.get("commit").get("sha").asText()).isNotBlank();
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

    }
}
