package com.example.Task.Management.API.service;

import com.example.Task.Management.API.model.Task;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskService {
    Page<Task> getAllTasks(int page, int size);

    Task getTasksByIds(int ids);

    Task createTask(@Valid Task task);

    Task updateTask(int id, @Valid Task task);

    void deleteTask(int id);

    List<Task> getTasksByUserId(int userId);
}
