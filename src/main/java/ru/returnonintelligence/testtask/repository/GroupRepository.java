package ru.returnonintelligence.testtask.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.returnonintelligence.testtask.model.Group;

import java.util.List;

/**
 * @author NIKIT on 27.07.2017.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAll();
    Group findByName(@NonNull String name);
}
