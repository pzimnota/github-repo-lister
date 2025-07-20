package com.githublisting.github_repo_lister.controller;

import com.githublisting.github_repo_lister.model.dto.RepositoryDTO;
import com.githublisting.github_repo_lister.service.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class GithubRepositoryController {

    private final GithubService githubService;

    public GithubRepositoryController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    public List<RepositoryDTO> getUserRepositories(@PathVariable String username) {
        return githubService.fetchUserRepositories(username);
    }
}
