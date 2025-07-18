package com.githublisting.github_repo_lister.model;


import java.util.List;

public class Repository {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;

    public Repository(){}


    public boolean isFork() {
        return fork;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public static class Owner{

        private String login;

        public Owner(){}

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

}
