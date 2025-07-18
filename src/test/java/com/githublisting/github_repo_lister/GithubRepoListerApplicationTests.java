package com.githublisting.github_repo_lister;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

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
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).contains("Repository Name");
		assertThat(response.getBody()).contains("Owner Login");
		assertThat(response.getBody()).contains("branches");
		assertThat(response.getBody()).contains("last commit sha");
		assertThat(response.getBody()).contains("Hello-World");
	}
}
