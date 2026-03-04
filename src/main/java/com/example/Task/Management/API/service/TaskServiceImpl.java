package com.example.Task.Management.API.service;

import com.example.Task.Management.API.exceptions.TaskNotFoundException;
import com.example.Task.Management.API.exceptions.UserNotFoundException;
import com.example.Task.Management.API.model.Task;
import com.example.Task.Management.API.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

private final TaskRepository taskRepository;
    @Override
    public Page<Task> getAllTasks(int page, int size) {
        log.info("Fetching tasks with pagination - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        log.debug("Constructed Pageable: {}", pageable);
        return taskRepository.findAll(pageable);
    }

    @Override
    public Task getTasksByIds(int ids) {
       Task tasks= taskRepository.findById((long) ids).orElseThrow(()->{
              log.info("Task not found with id: {}", ids);
             return
                   new TaskNotFoundException("Task not found with id: " + ids);
       });
       log.info("Fetched task with id: {}", ids);
       return tasks;

    }

    @Override
    public Task createTask(Task task) {
            Task createdTask = taskRepository.save(task);
            log.info("Created new task with id: {}", createdTask.getId());
            return createdTask;

    }

    @Override
    public Task updateTask(int id, Task task) {
            Task existingTask = taskRepository.findById((long) id).orElseThrow(
                    () -> {
                        log.info("Task not found with id: {}", id);
                        return new TaskNotFoundException("Task not found with id: " + id);
                    }
            );
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
            Task updatedTask = taskRepository.save(existingTask);
            log.info("Updated task with id: {}", updatedTask.getId());
            return updatedTask;

    }

    @Override
    public void deleteTask(int id) {
            if (!taskRepository.existsById((long) id)) {
                log.info("Task not found for deletion with id: {}", id);
                throw new TaskNotFoundException("Task not found with id: " + id);
            }
            taskRepository.deleteById((long) id);
            log.info("Deleted task with id: {}", id);

    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        if(!taskRepository.existsByUserId((long) userId)){
            log.info("No tasks found for user id: {}", userId);
            throw new UserNotFoundException("No tasks found for user id: " + userId);
        }
         List<Task> tasks = taskRepository.findByUserId((long) userId);
         log.info("Fetched {} tasks for user id: {}", tasks.size(), userId);
         return tasks;
    }

}
