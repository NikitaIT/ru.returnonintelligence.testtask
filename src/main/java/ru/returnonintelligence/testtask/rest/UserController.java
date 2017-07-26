package ru.returnonintelligence.testtask.rest;

import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.service.UserService;
import ru.returnonintelligence.testtask.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by fan.jin on 2016-10-15.
 */

@RestController
public class UserController {

    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    //-------------------Retrieve All Users--------------------------------------------------------

    @RequestMapping( method = RequestMethod.GET, value= "/user/all")
    public ResponseEntity<List<User>> getUsers() {
        LOGGER.debug("Received request to get all User");
        List<User> departures = userService.getAll();
        if (departures.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("Усё плохо"),HttpStatus.NO_CONTENT);
            // many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(departures, HttpStatus.OK);
    }
    //http://localhost:1234/user?username=adm&birthday=2005-09-20
    @RequestMapping( method = RequestMethod.GET, value= "/user")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(name = "username", required=false) String username,
            @RequestParam(name = "birthday", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthday,
            @RequestParam(name = "email", required=false) String email
            ) {
        if (username!=null){
            System.out.println("Received request to get User with username"+ username);
            List<User> users = userService.getByUsernameContaining(username);
            if (users.isEmpty()) {
                return new ResponseEntity(new CustomErrorType(" User with username"+ username +"NOT_FOUND"),HttpStatus.NOT_FOUND);
                // many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
        if (birthday!=null){
            System.out.println("Fetching User with birthday " + birthday);
            List<User> users = userService.getAllByBirthday(birthday);
            if (users.isEmpty()) {
                return new ResponseEntity(new CustomErrorType("User with birthday "+ birthday+" NOT_FOUND"),HttpStatus.NOT_FOUND);
                // many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }
        if (email!=null) {
            System.out.println("Fetching User with email " + email);
            Optional<User> user = userService.getByEmail(email);
            if (!user.isPresent()) {
                return new ResponseEntity(new CustomErrorType("User with email " + email + " NOT_FOUND"), HttpStatus.NOT_FOUND);
                // many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<User>>(Arrays.asList(user.get()), HttpStatus.OK);
        }
        return new ResponseEntity(new CustomErrorType("Bad RequestParams"),HttpStatus.NOT_FOUND);
    }
    /*
     *  We are not using userService.findByUsername here(we could),
     *  so it is good that we are making sure that the user has role "ROLE_USER"
     *  to access this endpoint.
     */
    @RequestMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public User user() {
        return (User)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }


    //-------------------Retrieve Single User--------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }



    //-------------------Create a User--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getUsername());

        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        Optional<User> currentUserO = userService.getById(id);

        if (!currentUserO.isPresent()) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        User currentUser = currentUserO.get();

        currentUser.setAddress(user.getAddress());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        Optional<User> user = userService.getById(id);
        if (!user.isPresent()) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
