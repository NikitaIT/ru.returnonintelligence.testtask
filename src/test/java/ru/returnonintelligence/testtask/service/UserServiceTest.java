package ru.returnonintelligence.testtask.service;

import ru.returnonintelligence.testtask.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

/**
 * Created by fan.jin on 2017-04-04.
 */
public class UserServiceTest extends AbstractTest {

    @Autowired
    UserService userService;

    @Test(expected = AccessDeniedException.class)
    public void testFindAllWithoutUser() throws AccessDeniedException {
        userService.getAll();
    }

    @Test(expected = AccessDeniedException.class)
    public void testFindAllWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.getAll();
    }

    @Test
    public void testFindAllWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        userService.getAll();
    }

    @Test(expected = AccessDeniedException.class)
    public void testFindByIdWithoutUser() throws AccessDeniedException {
        userService.getById(1L);
    }

    @Test(expected = AccessDeniedException.class)
    public void testFindByIdWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.getById(1L);
    }

    @Test
    public void testFindByIdWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        userService.getById(1L);
    }


    @Test(expected = AccessDeniedException.class)
    public void testFindByUsernameWithoutUser() throws AccessDeniedException {
        userService.getByUsername("user");
    }

    @Test
    public void testFindByUsernameWithUser() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestUser());
        userService.getByUsername("user");
    }

    @Test
    public void testFindByUsernameWithAdmin() throws AccessDeniedException {
        mockAuthenticatedUser(buildTestAdmin());
        userService.getByUsername("user");
    }

}
