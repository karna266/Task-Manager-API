package com.example.Task.Management.API.service;

import com.example.Task.Management.API.dto.AuthResponse;
import com.example.Task.Management.API.exceptions.UserNotFoundException;
import com.example.Task.Management.API.model.Task;
import com.example.Task.Management.API.model.User;
import com.example.Task.Management.API.repository.TaskRepository;
import com.example.Task.Management.API.repository.UserRepository;
import com.example.Task.Management.API.util.JwtUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Builder
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private  final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User createUser(User user) {
            User createdUser = userRepository.save(user);
            log.info("Created new user with id: {}", createdUser.getId());
            return createdUser;
    }

    @Override
    public User getUserByUsername(String username) {
      User fetchedUser=  userRepository.findByUsername(username).orElseThrow(() -> {
            log.info("User not found with username: {}", username);
            return new UserNotFoundException("User not found with username: " + username);
        });
        log.info("Fetched user with username: {}", fetchedUser.getUsername());
        return fetchedUser;

    }

    @Override
    public AuthResponse authenticateUser(String username, String password) {
        User user= userRepository.findByUsernameAndPassword(username, password).orElseThrow(() -> {
            log.info("Authentication failed for username: {}", username);
            return new UserNotFoundException("Invalid username or password");
        });
        if(!passwordEncoder.matches(user.getPassword(), password)){
            log.warn("Authentication failed for username: {}", username);
            throw new UserNotFoundException("Invalid username or password");
        }
        log.info("Authentication successful for username: {}", username);
            String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));
            log.debug("Generated JWT token for username: {}", username);
            return new AuthResponse(token,user.getUsername(), user.getRole());



    }

    @Override
    public List<Task> getAllTasksForAdmin() {
        log.info("Fetching all tasks for admin");
        List<Task> tasks = taskRepository.findAll();
        log.info("Fetched {} tasks for admin", tasks.size());
        return tasks;
    }
}
