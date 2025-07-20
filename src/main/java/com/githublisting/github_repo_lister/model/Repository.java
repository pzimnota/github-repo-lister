package com.githublisting.github_repo_lister.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Repository(String name, Owner owner, @JsonProperty("fork") boolean isFork, String branches_url) {

}
