package com.githublisting.github_repo_lister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BranchResponse {
    private final String name;
    @JsonProperty("last commit sha")
    private final String lastCommitSha;

    public BranchResponse(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }

}
