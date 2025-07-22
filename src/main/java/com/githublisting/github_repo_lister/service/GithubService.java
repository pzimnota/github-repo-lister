package com.githublisting.github_repo_lister.service;

import com.githublisting.github_repo_lister.exception.UserNotFoundException;
import com.githublisting.github_repo_lister.model.Branch;
import com.githublisting.github_repo_lister.model.Repository;
import com.githublisting.github_repo_lister.model.dto.BranchDTO;
import com.githublisting.github_repo_lister.model.dto.RepositoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Predicate;


@Service
public class GithubService {

    private static final Logger logger = LoggerFactory.getLogger(GithubService.class);
    private final RestClient restClient;

    public GithubService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<RepositoryDTO> fetchUserRepositories(String username) {

        logger.info("Fetching repositories for user '{}'", username);

        try {
            return getRepos(username).stream()
                    .filter(Predicate.not(Repository::isFork))
                    .map(repo -> new RepositoryDTO(
                            repo.name(),
                            repo.owner().login(),
                            getBranches(repo).stream()
                                    .map(branch -> new BranchDTO(
                                            branch.name(),
                                            branch.commit().sha()
                                    ))
                                    .toList()
                    ))
                    .toList();

        } catch (HttpClientErrorException.NotFound e) {
            logger.error("User '{}' not found on GitHub", username);
            throw new UserNotFoundException("User not found");
        }
    }

    private List<Repository> getRepos(String username) {
        Repository[] repos = restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(Repository[].class);
        if (repos == null) {
            logger.error("GitHub API returned null fetching repos for user '{}'", username);
            throw new IllegalStateException("Github returned null instead of empty array");
        }
        logger.debug("Fetched {} repositories for user '{}'", repos.length, username);
        return List.of(repos);
    }

    private List<Branch> getBranches(Repository repo) {
        String url = repo.branches_url().replace("{/branch}", "");
        Branch[] branches = restClient.get()
                .uri(url)
                .retrieve()
                .body(Branch[].class);

        if (branches == null) {
            logger.error("GitHub API returned null fetching branches for repo '{}'", repo.name());
            throw new IllegalStateException("Github returned null instead of branches");
        }
        logger.debug("Fetched {} branches for repo '{}'", branches.length, repo.name());
        return List.of(branches);
    }
}
