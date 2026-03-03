package com.example.Task.Management.API.exceptions;

public class TaskNotFoundException extends RuntimeException     {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
