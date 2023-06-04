package ru.hammer2000.springapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.hammer2000.springapp.model.User;
import ru.hammer2000.springapp.services.UsersService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        User newUser = (User) o;

        Optional<User> user = usersService.existingUser(newUser);

        if (user.isPresent()) {
            errors.rejectValue("password", "", "Этот пользователь уже существует");
        }
    }
}