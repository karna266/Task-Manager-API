package com.example.Task.Management.API.repository;

import com.example.Task.Management.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
