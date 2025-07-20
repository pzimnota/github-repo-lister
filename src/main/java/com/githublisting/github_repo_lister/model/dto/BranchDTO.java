package com.githublisting.github_repo_lister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchDTO(String name, @JsonProperty("last commit sha") String lastCommitSha) {

}
