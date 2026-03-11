package com.example.Task.Management.API.service;

import com.example.Task.Management.API.dto.AuthResponse;
import com.example.Task.Management.API.model.Task;
import com.example.Task.Management.API.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserByUsername(String username);


    AuthResponse authenticateUser(String username, String password);

    List<Task> getAllTasksForAdmin();
}
