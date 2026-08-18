# Task Manager CLI

A small interactive task manager built with Java 21 and Maven. Tasks are kept in memory and are discarded when the application exits.

## Requirements

- JDK 21
- Maven 3.9 or newer

## Build and test

```bash
mvn clean package
```

Run only the tests with:

```bash
mvn test
```

## Run

After packaging the application, run:

```bash
java -jar target/task-manager-cli-1.0-SNAPSHOT.jar
```

The CLI supports these commands:

```text
create <description>  Create a task
list                  List all tasks
complete <id>         Mark a task as completed
delete <id>           Delete a task
help                  Show command help
exit                  Exit the application
```

Example session:

```text
> create Write project documentation
Created task 1.
> list
1. [ ] Write project documentation
> complete 1
Completed task 1.
> list
1. [x] Write project documentation
> delete 1
Deleted task 1.
> exit
Goodbye.
```
