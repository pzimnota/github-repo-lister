package com.githublisting.github_repo_lister.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RepositoryDTO(@JsonProperty("Repository Name") String repositoryName,
                            @JsonProperty("Owner Login") String ownerLogin, List<BranchDTO> branches) {

}
