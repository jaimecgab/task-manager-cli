package com.taskmanager.service;

import com.taskmanager.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {
    private final TaskService service = new TaskService();

    @Test
    void createsAndListsTasksInCreationOrder() {
        Task first = service.create("Write documentation");
        Task second = service.create("Run tests");

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
        assertEquals(2, service.list().size());
        assertEquals("Write documentation", service.list().getFirst().getDescription());
        assertFalse(first.isCompleted());
    }

    @Test
    void trimsDescriptionAndRejectsBlankDescriptions() {
        Task task = service.create("  Review code  ");

        assertEquals("Review code", task.getDescription());
        assertThrows(IllegalArgumentException.class, () -> service.create("   "));
        assertThrows(IllegalArgumentException.class, () -> service.create(null));
        assertEquals(2, service.create("Next task").getId());
    }

    @Test
    void completesAnExistingTask() {
        Task task = service.create("Ship release");

        Task completed = service.complete(task.getId());

        assertTrue(completed.isCompleted());
    }

    @Test
    void deletesAnExistingTask() {
        Task task = service.create("Temporary task");

        service.delete(task.getId());

        assertTrue(service.list().isEmpty());
    }

    @Test
    void rejectsInvalidOrUnknownIds() {
        assertThrows(IllegalArgumentException.class, () -> service.complete(0));
        assertThrows(IllegalArgumentException.class, () -> service.delete(-1));
        assertThrows(TaskNotFoundException.class, () -> service.complete(99));
        assertThrows(TaskNotFoundException.class, () -> service.delete(99));
    }
}
