package com.githublisting.github_repo_lister;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GithubRepoListerApplicationTests {

    private WireMockServer wireMockServer;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void setupWiremock(){
        wireMockServer = new WireMockServer(9561);
        wireMockServer.start();
        setupStubs();
    }

    private void setupStubs() {

        wireMockServer.stubFor(get(urlEqualTo("/users/octocat/repos"))
                .willReturn(okJson("""
                        [
                          {
                            "name": "git-consortium",
                            "owner": { "login": "octocat" },
                            "fork": false,
                            "branches_url": "http://localhost:9561/repos/octocat/git-consortium/branches{/branch}"
                          },
                          {
                            "name": "forked-repo",
                            "owner": { "login": "octocat" },
                            "fork": true,
                            "branches_url": "http://localhost:9561/repos/octocat/forked-repo/branches{/branch}"
                          }
                        ]
                        """)));

        wireMockServer.stubFor(get(urlEqualTo("/repos/octocat/git-consortium/branches"))
                .willReturn(okJson("""
                        [
                          {
                            "name": "main",
                            "commit": { "sha": "abc123" }
                          },
                          {
                            "name": "dev",
                            "commit": { "sha": "def456" }
                          }
                        ]
                        """)));
    }

    @AfterAll
    void stopWiremock(){
        if (wireMockServer != null){
            wireMockServer.stop();
        }
    }

    @Test
    void givenValidUser_whenCallingApi_thenReturnCorrectRepositoryData() throws Exception {
        // given

        String username = "octocat";
        String url = "/repositories/" + username;

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var objectMapper = new ObjectMapper();

            var root = objectMapper.readTree(response.getBody());


            assertThat(root.isArray()).isTrue();
            assertThat(root.size()).isEqualTo(1);

            var repo = root.get(0);
            assertThat(repo.path("Repository Name").asText()).isEqualTo("git-consortium");
            assertThat(repo.path("Owner Login").asText()).isEqualTo("octocat");

            var branches = repo.path("branches");
            assertThat(branches).isNotNull().isNotEmpty();

            var branch = branches.get(0);
            assertThat(branch.path("name").asText()).isEqualTo("main");
            assertThat(branch.path("last commit sha").asText()).isEqualTo("abc123");


    }
}