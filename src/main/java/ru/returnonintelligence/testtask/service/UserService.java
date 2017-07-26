package ru.returnonintelligence.testtask.service;

import lombok.NonNull;
import ru.returnonintelligence.testtask.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface UserService {
    List<User> getAll();
    Optional<User> getByUsername(@NonNull String username);
    Optional<User> getByEmail(@NonNull String email);
    List<User> getAllByBirthday(@NonNull LocalDate birthday);
    List<User> getByUsernameContaining(String username);
    void setAll(List<User> userList);
    Optional<User> getById(@NonNull Long id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(long id);
    void deleteAllUsers();
    public boolean isUserExist(User user);
}
