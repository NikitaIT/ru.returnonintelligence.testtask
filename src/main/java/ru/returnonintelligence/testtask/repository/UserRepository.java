package ru.returnonintelligence.testtask.repository;

import ru.returnonintelligence.testtask.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //if u want custom
    //@Query("select d from User d where d.id = :id")
    //User findById(@Param("id") long id);

    User findByUsername(@NonNull String username);
    List<User> findAllByBirthday(@NonNull LocalDate birthday);
    User findByEmail(@NonNull String email);
    User findById(@NonNull Long id);
    List<User> findAllByUsernameContaining(String username);
    List<User> findAllByUsernameContainingAndIsActive(String username,boolean isActive);
    List<User> findAllByIsActive(boolean isActive);
    User findByEmailAndIsActive(@NonNull String email, boolean isActive);
    List<User>  findAllByBirthdayAndIsActive(@NonNull LocalDate birthday, boolean isActive);
}

