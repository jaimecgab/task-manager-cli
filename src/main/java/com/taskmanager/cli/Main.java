package com.taskmanager.cli;

import com.taskmanager.service.TaskService;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        new TaskManagerCli(new TaskService(), System.in, System.out).run();
    }
}
