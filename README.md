# Github Repo Lister

A simple Spring Boot application that fetches and lists public GitHub repositories for a given user, excluding forks. For each repository, it shows the repository name, owner login, and branches with the latest commit SHA.

## Features

- Lists all non-fork repositories of a GitHub user
- Shows repository name and owner login
- Lists branches with last commit SHA for each repository
- Returns a well-structured JSON response
- Handles non-existing users with proper 404 error response

## Technologies Used

- Java 21
- Spring Boot 3.5
- Spring Web (RestTemplate) for REST API consumption
- JUnit 5 for integration testing

## Setup

1. **Clone the repository**

```bash
git clone https://github.com/pzimnota/github-repo-lister.git
cd github-repo-lister
```

2. **Configure GitHub Token**
Inside the file src/main/resources/application.properties fill:
```properties
github.token=your_github_token_here
```
Alternatively, you can set the environment variable named ```GITHUB_TOKEN```, or configure it as a system variable in IntelliJ.

You can generate a personal access token [here](https://github.com/settings/tokens).

3. **Build and run the application**
Using Maven:

```bash
./mvnw spring-boot:run
```

Or build the jar and run:
```bash
./mvnw clean package
java -jar target/github-repo-lister-0.0.1-SNAPSHOT.jar
```

## Test the API

Open your browser or use curl/Postman to access:

```bash
http://localhost:8080/repositories/octocat
```
Replace ```octocat``` with any GitHub username.

The API responds with JSON formatted like:
```json
{
    "Repository Name": "Hello-World",
    "Owner Login": "octocat",
    "branches": [
      {
        "name": "master",
        "last commit sha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
      },
      {
        "name": "octocat-patch-1",
        "last commit sha": "b1b3f9723831141a31a1a7252a213e216ea76e56"
      },
      {
        "name": "test",
        "last commit sha": "b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf"
      }
    ]
  },
```
## Testing
Run integration tests with:

```bash
./mvnw test
```

## Notes
- This project does not handle pagination on the GitHub API.
- Only public repositories are listed.
- Forked repositories are excluded.

