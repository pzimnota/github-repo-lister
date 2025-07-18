package com.githublisting.github_repo_lister.service;

import com.githublisting.github_repo_lister.exception.UserNotFoundException;
import com.githublisting.github_repo_lister.model.Branch;
import com.githublisting.github_repo_lister.model.Repository;
import com.githublisting.github_repo_lister.model.dto.BranchResponse;
import com.githublisting.github_repo_lister.model.dto.RepositoryResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    @Value("${github.token}")
    private String githubToken;

    private RestTemplate restTemplate;

    @PostConstruct
    private void createAuthRestTemplate(){
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + githubToken);
            return execution.execute(request, body);
        }));
    }



    public List<RepositoryResponse> fetchUserRepositories(String username){
        String url = "https://api.github.com/users/" + username + "/repos";

        try{
            Repository[] repos = restTemplate.getForObject(url, Repository[].class);
            if (repos == null) return List.of();

            List<Repository> nonForkRepos =  Arrays.stream(repos)
                    .filter(repo -> !repo.isFork())
                    .toList();

            for(Repository repo : nonForkRepos) {
                String branchesUrl = "https://api.github.com/repos/"
                        + repo.getOwner().getLogin() + "/" + repo.getName() + "/branches";
                Branch[] branches = restTemplate.getForObject(branchesUrl, Branch[].class);
                repo.setBranches(List.of(branches));
//                System.out.println("Fetching branches for: " + repo.getName());
//                System.out.println("Calling URL: " + branchesUrl);
//                System.out.println(Arrays.toString(branches));
            }
                return nonForkRepos.stream()
                        .map(repo -> new RepositoryResponse(
                                repo.getName(),
                                repo.getOwner().getLogin(),
                                repo.getBranches().stream()
                                        .map(branch -> new BranchResponse(
                                                branch.getName(),
                                                branch.getCommit().getSha()
                                        ))
                                        .collect(Collectors.toList())
                                ))
                        .collect(Collectors.toList());

        }
        catch (HttpClientErrorException.NotFound e){
            throw new UserNotFoundException("User not found");
        }
    }
}
