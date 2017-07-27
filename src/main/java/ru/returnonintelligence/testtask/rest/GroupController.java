package ru.returnonintelligence.testtask.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.returnonintelligence.testtask.model.Group;
import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.service.GroupService;
import ru.returnonintelligence.testtask.service.UserService;
import ru.returnonintelligence.testtask.util.CustomErrorType;

import java.util.List;
import java.util.Set;

/**
 * @author NIKIT on 27.07.2017.
 */
@RestController
@RequestMapping(value= "/group")
public class GroupController {

    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @RequestMapping( method = RequestMethod.GET, value= "/all")
    public ResponseEntity<List<Group>> getGroups() {
        LOGGER.debug("Received request to get all group");
        List<Group> groups = groupService.getAll();
        if (groups.isEmpty()) {
            return new ResponseEntity(
                    new CustomErrorType("Bad request, NO_CONTENT"),
                    HttpStatus.NO_CONTENT);
            // many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
    }
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Set<User>> getUsers(@RequestParam(name = "gruopname") String gruopname) {
        LOGGER.debug("Received request to get all group");
        Set<User> users = groupService.getUsersByGroupName(gruopname);
        if (users.isEmpty()) {
            return new ResponseEntity(
                    new CustomErrorType("Bad request, NOT_FOUND"),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
