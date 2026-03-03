package com.example.Task.Management.API.controller;


import com.example.Task.Management.API.model.Task;
import com.example.Task.Management.API.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;



    @GetMapping
    public ResponseEntity<Page<Task>> getAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        log.info("Received request to get all tasks with page: {} and size: {}", page, size);
        Page<Task> tasks = taskService.getAllTasks(page, size);
        return ResponseEntity.ok(tasks);
    }

   @GetMapping("/{ids}")
    public ResponseEntity<Task> getTasksById(@Valid @PathVariable int ids) {
        log.info("Received request to get tasks by IDs: {}", ids);
         Task fetchedTask = taskService.getTasksByIds(ids);
        return ResponseEntity.ok(fetchedTask);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        log.info("Received request to create task: {}", task);
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @Valid @RequestBody Task task) {
        log.info("Received request to update task with ID: {}", id);
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        log.info("Received request to delete task with ID: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable int userId) {
        log.info("Received request to get tasks for user ID: {}", userId);
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
}