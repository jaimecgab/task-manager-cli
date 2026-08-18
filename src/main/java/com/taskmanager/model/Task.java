package com.taskmanager.model;

public final class Task {
    private final long id;
    private final String description;
    private boolean completed;

    public Task(long id, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be blank.");
        }

        this.id = id;
        this.description = description.strip();
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        completed = true;
    }
}
