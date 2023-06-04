package ru.hammer2000.springapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hammer2000.springapp.model.Person;
import ru.hammer2000.springapp.model.User;
import ru.hammer2000.springapp.repositories.UsersRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> existingUser(User user) {
        return usersRepository.findByUsername(user.getUsername());
    }
}
