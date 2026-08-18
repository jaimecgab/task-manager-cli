# Repository Guidance

- This is a Maven project targeting Java 21, as configured in `pom.xml`.
- Production source code lives under `src/main/java`; the application entry point is `com.taskmanager.cli.Main`.
- Build the project with `mvn compile` or package it with `mvn package`.
- Run the current entry point after compiling with `java -cp target/classes com.taskmanager.cli.Main`.
- There are currently no dependencies, automated tests, lint configuration, or task-management features.
