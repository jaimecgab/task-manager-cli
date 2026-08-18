package com.taskmanager.service;

public final class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long id) {
        super("Task with ID " + id + " was not found.");
    }
}
