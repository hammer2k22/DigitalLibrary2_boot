package ru.hammer2000.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hammer2000.springapp.model.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
