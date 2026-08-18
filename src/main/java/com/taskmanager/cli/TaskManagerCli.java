package com.taskmanager.cli;
import java.util.Locale;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskNotFoundException;
import com.taskmanager.service.TaskService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public final class TaskManagerCli {
    private final TaskService taskService;
    private final Scanner scanner;
    private final PrintStream output;

    public TaskManagerCli(TaskService taskService, InputStream input, PrintStream output) {
        this.taskService = taskService;
        this.scanner = new Scanner(input);
        this.output = output;
    }

    public void run() {
        output.println("Task Manager CLI");
        printHelp();

        while (true) {
            output.print("> ");
            if (!scanner.hasNextLine()) {
                output.println();
                return;
            }

            String line = scanner.nextLine().strip();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase(Locale.ROOT);
            String argument = parts.length == 2 ? parts[1].strip() : "";

            try {
                switch (command) {
                    case "create" -> createTask(argument);
                    case "list" -> listTasks(argument);
                    case "complete" -> completeTask(argument);
                    case "delete" -> deleteTask(argument);
                    case "help" -> {
 				 requireNoArgument("help", argument);
   				 printHelp();
			}
		    case "exit" -> {
		    requireNoArgument("exit", argument);
		    output.println("Goodbye.");
		    return;
			}
                 default -> output.println("Error: Unknown command. Type 'help' to see available commands.");
                }
            } catch (IllegalArgumentException | TaskNotFoundException exception) {
                output.println("Error: " + exception.getMessage());
            }
        }
    }

    private void createTask(String description) {
        Task task = taskService.create(description);
        output.printf("Created task %d.%n", task.getId());
    }

    private void listTasks(String argument) {
        requireNoArgument("list", argument);
        List<Task> tasks = taskService.list();
        if (tasks.isEmpty()) {
            output.println("No tasks.");
            return;
        }

        for (Task task : tasks) {
            output.printf("%d. [%s] %s%n", task.getId(), task.isCompleted() ? "x" : " ", task.getDescription());
        }
    }

    private void completeTask(String argument) {
        long id = parseId("complete", argument);
        taskService.complete(id);
        output.printf("Completed task %d.%n", id);
    }

    private void deleteTask(String argument) {
        long id = parseId("delete", argument);
        taskService.delete(id);
        output.printf("Deleted task %d.%n", id);
    }

    private long parseId(String command, String argument) {
        if (argument.isBlank()) {
            throw new IllegalArgumentException("Usage: " + command + " <id>");
        }

        try {
            return Long.parseLong(argument);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Task ID must be a whole number.");
        }
    }

    private void requireNoArgument(String command, String argument) {
        if (!argument.isBlank()) {
            throw new IllegalArgumentException("Usage: " + command);
        }
    }

    private void printHelp() {
        output.println("Commands:");
        output.println("  create <description>  Create a task");
        output.println("  list                  List all tasks");
        output.println("  complete <id>         Mark a task as completed");
        output.println("  delete <id>           Delete a task");
        output.println("  help                  Show this help");
        output.println("  exit                  Exit the application");
    }
}
