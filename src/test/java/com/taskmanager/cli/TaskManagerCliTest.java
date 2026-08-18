package com.taskmanager.cli;

import com.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskManagerCliTest {
    @Test
    void runsMainTaskFlow() {
        String result = runCli("""
                create Write documentation
                list
                complete 1
                list
                delete 1
                list
                exit
                """);

        assertTrue(result.contains("Created task 1."));
        assertTrue(result.contains("1. [ ] Write documentation"));
        assertTrue(result.contains("Completed task 1."));
        assertTrue(result.contains("1. [x] Write documentation"));
        assertTrue(result.contains("Deleted task 1."));
        assertTrue(result.contains("No tasks."));
        assertTrue(result.contains("Goodbye."));
    }

    @Test
    void reportsInputErrorsAndKeepsRunning() {
        String result = runCli("""
                create
                complete abc
                delete 42
                unknown
                exit
                """);

        assertTrue(result.contains("Error: Task description cannot be blank."));
        assertTrue(result.contains("Error: Task ID must be a whole number."));
        assertTrue(result.contains("Error: Task with ID 42 was not found."));
        assertTrue(result.contains("Error: Unknown command."));
        assertTrue(result.contains("Goodbye."));
    }

    private String runCli(String commands) {
        ByteArrayInputStream input = new ByteArrayInputStream(commands.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        new TaskManagerCli(new TaskService(), input, new PrintStream(output, true, StandardCharsets.UTF_8)).run();

        return output.toString(StandardCharsets.UTF_8);
    }
}
