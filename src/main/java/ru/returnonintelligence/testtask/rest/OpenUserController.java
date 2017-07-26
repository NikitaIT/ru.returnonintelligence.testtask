package ru.returnonintelligence.testtask.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.model.UserDTO;
import ru.returnonintelligence.testtask.service.GroupService;
import ru.returnonintelligence.testtask.service.UserService;
import ru.returnonintelligence.testtask.util.CustomErrorType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author NIKIT on 27.07.2017.
 */
@RestController
@RequestMapping(value= "/open/")
public class OpenUserController {
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping( method = RequestMethod.GET, value= "/user/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        LOGGER.debug("Received request to get all User");
        List<User> users = userService.getAllByIsActive(true);
        if (users.isEmpty()) {
            return new ResponseEntity(
                    new CustomErrorType("Bad request, NO_CONTENT"),
                    HttpStatus.NO_CONTENT);
            // many decide to return HttpStatus.NOT_FOUND
        }
        List<UserDTO> userDTOList = new ArrayList<>();
        users.forEach((user)->{
            userDTOList.add(UserDTO
                    .builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .birthday(user.getBirthday())
                    .build());
        });
        return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
    }
    //http://localhost:1234/user?username=adm&birthday=2005-09-20
    @RequestMapping( method = RequestMethod.GET, value= "/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDTO>> getUsers(
            @RequestParam(name = "username", required=false) String username,
            @RequestParam(name = "birthday", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
            @RequestParam(name = "email", required=false) String email
    ) {
        List<UserDTO> userDTOList = new ArrayList<>();
        if (username!=null){
            System.out.println("Received request to get User with username"+ username);
            List<User> users = userService.getAllByUsernameContainingAndIsActive(username,true);
            if (users.isEmpty()) {
                return new ResponseEntity(new CustomErrorType(" User with username"+ username +"NOT_FOUND"),HttpStatus.NOT_FOUND);
                // many decide to return HttpStatus.NOT_FOUND
            }
            users.forEach((user)->{
                userDTOList.add(UserDTO
                        .builder()
                        .id(user.getId())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .birthday(user.getBirthday())
                        .build());
            });
            return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
        }
        if (birthday!=null){
            System.out.println("Fetching User with birthday " + birthday);
            List<User> users = userService.getAllByBirthdayAndIsActive(birthday,true);
            if (users.isEmpty()) {
                return new ResponseEntity(new CustomErrorType("User with birthday "+ birthday+" NOT_FOUND"),HttpStatus.NOT_FOUND);
                // many decide to return HttpStatus.NOT_FOUND
            }
            users.forEach((user)->{
                userDTOList.add(UserDTO
                        .builder()
                        .id(user.getId())
                        .firstname(user.getFirstname())
                        .lastname(user.getLastname())
                        .email(user.getEmail())
                        .birthday(user.getBirthday())
                        .build());
            });
            return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
        }
        if (email!=null) {
            System.out.println("Fetching User with email " + email);
            Optional<User> user = userService.getByEmailAndIsActive(email,true);
            if (!user.isPresent()) {
                return new ResponseEntity(new CustomErrorType("User with email " + email + " NOT_FOUND"), HttpStatus.NOT_FOUND);
                // many decide to return HttpStatus.NOT_FOUND
            }
            userDTOList.add(UserDTO
                        .builder()
                        .id(user.get().getId())
                        .firstname(user.get().getFirstname())
                        .lastname(user.get().getLastname())
                        .email(user.get().getEmail())
                        .birthday(user.get().getBirthday())
                        .build());

            return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Bad RequestParams"),HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public User user() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
