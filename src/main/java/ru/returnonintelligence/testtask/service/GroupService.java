package ru.returnonintelligence.testtask.service;

import ru.returnonintelligence.testtask.model.Group;
import ru.returnonintelligence.testtask.model.User;

import java.util.List;
import java.util.Set;

/**
 * @author NIKIT on 27.07.2017.
 */
public interface GroupService {
    List<Group> getAll();
    Set<User> getUsersByGroupName(String name);
    int countAdmins();
}
