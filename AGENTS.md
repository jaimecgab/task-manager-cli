# Repository Guidance

- This is a Maven project targeting Java 21, as configured in `pom.xml`.
- Production source code lives under `src/main/java`; the application entry point is `com.taskmanager.cli.Main`.
- Build the project with `mvn compile` or package it with `mvn package`.
- Run the current entry point after compiling with `java -cp target/classes com.taskmanager.cli.Main`.
- The application provides an in-memory task manager CLI with commands to create, list, complete, and delete tasks.
- Automated tests use JUnit 5 and live under `src/test/java`; run them with `mvn test`.
- Run the full build and test suite with `mvn package`.
