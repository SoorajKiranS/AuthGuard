package com.AuthGuard.AuthGuard.repository;

import com.AuthGuard.AuthGuard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
//    Talks to the database to save or fetch User objects.
    Optional<User> findByUsername(String username);
}
