# Github Repo Lister

A simple Spring Boot application that fetches and lists public GitHub repositories for a given user, **excluding forks**. For each repository, it shows:

- Repository name  
- Owner login  
- Branches with the latest commit SHA  

## ✅ Features

- Lists all non-fork public repositories of a GitHub user
- Provides repository name and owner login
- Lists all branches with the latest commit SHA
- Returns a well-structured JSON response
- Handles non-existing users with a proper 404 response

## 🚀 Technologies Used

- Java 21  
- Spring Boot 3.5.3  
- Spring RestClient (Spring 6+)  
- WireMock for mocking external API in tests  
- JUnit 5 for integration testing  

## ⚙️ Setup

### 1. Clone the repository

```bash
git clone https://github.com/pzimnota/github-repo-lister.git
cd github-repo-lister
```

### 2. Configure GitHub Token

To authorize requests to the GitHub API, provide your personal access token.

#### Option A: via `application.properties`

Create or edit the file `src/main/resources/application.properties` and add:

```properties
github.token=your_github_token_here
github.api.base-url=https://api.github.com
```

#### Option B: via environment variable

To securely provide your GitHub token, you can set it as an environment variable in IntelliJ:

1. Open **Run → Edit Configurations…**
2. Select your application's run configuration, or click `+` and choose **Application**
3. In the **Environment variables** section, click the `...` button
4. Add a new variable:
    - **Name**: `GITHUB_TOKEN`
    - **Value**: `your_github_token_here`
5. Click **OK**, then **Apply** to save the configuration
6. Run the application using this configuration

You can generate a token here:  
👉 https://github.com/settings/tokens → Developer settings → Personal access tokens

### 3. Build and run the application

Using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Or manually:

```bash
./mvnw clean package
java -jar target/github-repo-lister-0.0.1-SNAPSHOT.jar
```

## 🔍 Example API Usage

```http
GET http://localhost:8080/repositories/octocat
```

Replace `octocat` with any GitHub username.

## 🧪 Running Tests

Edit in progress

## 📌 Notes

- Pagination from the GitHub API is not handled
- Only **public**, **non-fork** repositories are listed
- Uses Spring’s `RestClient` instead of deprecated `RestTemplate`
- JSON keys are tailored to match the requirements:
  - `"Repository Name"`  
  - `"Owner Login"`  
  - `"branches"` (with `"last commit sha"`)

---

Feel free to submit issues or suggestions via GitHub.
