package ru.hammer2000.springapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.hammer2000.springapp.model.User;
import ru.hammer2000.springapp.repositories.UsersRepository;
import ru.hammer2000.springapp.security.UsersDetails;

import java.util.Optional;


@Service
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден");

        return new UsersDetails(user.get());
    }
}
