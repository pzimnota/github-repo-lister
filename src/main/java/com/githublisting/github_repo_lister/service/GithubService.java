package com.githublisting.github_repo_lister.service;

import com.githublisting.github_repo_lister.exception.UserNotFoundException;
import com.githublisting.github_repo_lister.model.Branch;
import com.githublisting.github_repo_lister.model.Repository;
import com.githublisting.github_repo_lister.model.dto.BranchDTO;
import com.githublisting.github_repo_lister.model.dto.RepositoryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Predicate;

@Service
public class GithubService {

    private final RestTemplate restTemplate;

    public GithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryDTO> fetchUserRepositories(String username) {

        try {
            return getRepos(username).stream()
                    .filter(Predicate.not(Repository::isFork))
                    .map(repository -> {
                        var branches = getBranches(repository);
                        return new RepositoryDTO(
                                repository.name(),
                                repository.owner().login(),
                                branches.stream()
                                        .map(branch -> new BranchDTO(
                                                branch.name(),
                                                branch.commit().sha()
                                        ))
                                        .toList()
                        );

                    })
                    .toList();

        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found");
        }
    }

    private List<Repository> getRepos(String username) {
        var url = "https://api.github.com/users/" + username + "/repos";
        var repos = restTemplate.getForObject(url, Repository[].class);
        if (repos == null) {
            return List.of();
        } else {
            return List.of(repos);
        }
    }

    private List<Branch> getBranches(Repository repo) {
        var url = repo.branches_url().replace("{/branch}", "");
        var branches = restTemplate.getForObject(url, Branch[].class);
        if (branches == null) {
            return List.of();
        } else {
            return List.of(branches);
        }
    }
}
