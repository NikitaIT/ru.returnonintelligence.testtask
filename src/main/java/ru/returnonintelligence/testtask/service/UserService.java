package ru.returnonintelligence.testtask.service;

import lombok.NonNull;
import ru.returnonintelligence.testtask.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    boolean isUserExist(User user);
    void reActivateUserByUsername(String username,boolean isActive);
    Long countAll();
    List<User> getAllByUsernameContainingAndIsActive(String username,boolean isActive);
    List<User> getAllByIsActive(boolean isActive);
    Optional<User> getByEmailAndIsActive(@NonNull String email,boolean isActive);
    List<User> getAllByBirthdayAndIsActive(@NonNull LocalDate birthday,boolean isActive);
}
