
# GitHub User Activity CLI

A simple CLI program that fetches the recent activity of a GitHub user, and displays it in the terminal.


## Usage

Run the ```github-activity``` command, passing the GitHub username as an argument.
```bash
  github-activity <username>
```
Output:
```bash
  Pushed to jordancmh/github-user-activity
  Created jordancmh/github-user-activity
  Pushed to jordancmh/springboot-ecom
```

## Features / How It Works

- Runs from the command line via Spring Shell.
- Accepts the GitHub username as an argument.
- Hits the GitHub API *events* endpoint to fetch recent GitHub activity in JSON format.
- Parses the JSON using the Jackson library, then displays the data back into the terminal in a readable format.
- Error Handling - 
  Handles errors for unsuccessful API calls:

  ```bash
    Error fetching activity. Status Code: 404
  ```
  and incorrect use of the ```github-activity``` command:
  ```bash
    Usage: github-activity <username>
  ```

## Run Locally

Clone the project and open its directory

```bash
  git clone https://github.com/jordancmh/github-user-activity
  cd github-user-activity
```

Build and run the project

```bash
  mvn clean install
  mvn spring-boot:run
```


## Technologies

- Java 21
- Spring Boot 3.4.6
- Maven
- Spring Shell
- Jackson library
- GitHub REST API

## Project Idea

[roadmap.sh](https://roadmap.sh/projects/github-user-activity)

## License

[MIT](https://choosealicense.com/licenses/mit/)
