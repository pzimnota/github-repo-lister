package com.githublisting.github_repo_lister.controller;

import com.githublisting.github_repo_lister.model.dto.RepositoryResponse;
import com.githublisting.github_repo_lister.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class GithubRepositoryController {

    private final GithubService githubService;

    public GithubRepositoryController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryResponse>> getUserRepositories(@PathVariable String username){
        List<RepositoryResponse> repositories = githubService.fetchUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}
