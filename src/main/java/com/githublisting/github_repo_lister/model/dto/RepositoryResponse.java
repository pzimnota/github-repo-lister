package com.githublisting.github_repo_lister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RepositoryResponse {
    @JsonProperty("Repository Name")
    private final String repositoryName;

    @JsonProperty("Owner Login")
    private final String ownerLogin;
    private final List<BranchResponse> branches;

    public RepositoryResponse(String repositoryName, String ownerLogin, List<BranchResponse> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<BranchResponse> getBranches() {
        return branches;
    }

}
