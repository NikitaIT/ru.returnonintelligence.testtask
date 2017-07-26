package ru.returnonintelligence.testtask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.returnonintelligence.testtask.model.Group;
import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.repository.GroupRepository;
import ru.returnonintelligence.testtask.service.GroupService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author NIKIT on 27.07.2017.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Set<User> getUsersByGroupName(String name) {
        Group group = groupRepository.findByName(name);
        if (group!=null){
            return group.getUsers();
        }
        return new HashSet<>();
    }

    @Override
    public int countAdmins() {
        return getUsersByGroupName("ROLE_ADMIN").size();
    }
}
