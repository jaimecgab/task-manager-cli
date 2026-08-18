package com.taskmanager.service;

import com.taskmanager.model.Task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskService {
    private final Map<Long, Task> tasks = new LinkedHashMap<>();
    private long nextId = 1;

    public Task create(String description) {
        Task task = new Task(nextId, description);
        nextId++;
        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> list() {
        return List.copyOf(tasks.values());
    }

    public Task complete(long id) {
        Task task = find(id);
        task.complete();
        return task;
    }

    public void delete(long id) {
        validateId(id);
        if (tasks.remove(id) == null) {
            throw new TaskNotFoundException(id);
        }
    }

    private Task find(long id) {
        validateId(id);
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException(id);
        }
        return task;
    }

    private void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive.");
        }
    }
}
