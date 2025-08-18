# Code Playground ![Backend](https://img.shields.io/badge/{_}-Backend-16A34A?style=flat-square)

![Java](https://img.shields.io/badge/Java-17+-red?logo=java&logoColor=white&style=flat)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.7+-green?logo=springboot&logoColor=white&style=flat)
![MariaDB](https://img.shields.io/badge/Database-MariaDB-lightblue?logo=mariadb&style=flat)
![Maven](https://img.shields.io/badge/Build-Maven-orange?logo=apachemaven&style=flat)
![Docker](https://img.shields.io/badge/Containerized-Docker-blue?logo=docker&style=flat)
![License](https://img.shields.io/github/license/shubham225/coding-test-backend?style=flat)

This repository contains the **backend service** for the `CodePlayground_` online coding platform. Built with **Java**,
**Spring Boot**, and **Docker**, it provides a secure, scalable environment to compile and run user-submitted code. It
communicates with the frontend via a RESTful API and stores data using **MariaDB**.

> 🧠 This backend powers secure code execution and problem management. Pair it with
> the [Frontend Repository](https://github.com/shubham225/online-coding-platform-frontend).

## ✨ Features

- **Secure Code Execution**: Utilizes **Docker** containers to execute user-submitted code in an isolated and safe
  environment, preventing any malicious code from affecting the system.
- **RESTful API**: Exposes a clean, REST API to handle user registration, problem submission, and code execution
  requests.
- **Database**: **MariaDB** is used to store and manage user data, coding problems, test results, and more.
- **Real-Time Results**: Provides real-time feedback to users based on their code execution results.

## 📥 Installation

### Prerequisites

- [Java 17+](https://openjdk.java.net/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/) (for building the project)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/shubham225/codeplayground-service.git
   cd codeplayground-service
   ```
2. Set up MariaDB:
    - You can use Docker to run MariaDB, or install it locally.
    - If using Docker, you can run the following command to start the database container:
   ```bash
    docker run --name mariadb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=coding_test -p 3306:3306 -d mariadb:latest
    ```
3. Build and run the backend:
    ```bash
    mvn clean install
    mvn spring-boot:run
   ```
4. The backend server will be running at:
    ```
   http://localhost:8080
   ```

## 🌐 API Endpoints

### Problem Management (Admin)

- **GET /api/v1/problems**: Get all coding problems
- **POST /api/v1/problems**: Add a new coding problem
- **GET /api/v1/problems/{id}**: Get a problem by Id
- **POST /api/v1/problems/{id}/codeSnippets**: add code snippets associated with a problem
- **POST /api/v1/problems/{id}/testcases**: add testcases for a problem statement

### Code Execution

- **POST /api/v1/submit**: Upload the code to backend, and submitted code to user problem.
- **POST /api/v1/execute**: Submit code for execution. The backend will execute the code in a Docker container and
  return the result.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙌 Acknowledgments

- Built using [Spring Boot](https://spring.io/projects/spring-boot) and [MariaDB](https://mariadb.org/).
- Docker is used to securely execute user-submitted code in isolated containers, ensuring a safe and reliable
  environment.
