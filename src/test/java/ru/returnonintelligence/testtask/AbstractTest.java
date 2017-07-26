package ru.returnonintelligence.testtask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.returnonintelligence.testtask.model.Authority;
import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.repository.UserRepository;
import ru.returnonintelligence.testtask.security.auth.AnonAuthentication;
import ru.returnonintelligence.testtask.security.auth.TokenBasedAuthentication;

import java.util.HashSet;
import java.util.Set;


@RunWith( SpringRunner.class )
@SpringBootTest(classes = { Application.class })
public abstract class AbstractTest {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	@Before
	public final void beforeAbstractTest() {
		securityContext = Mockito.mock( SecurityContext.class );
		SecurityContextHolder.setContext( securityContext );
		Mockito.when( securityContext.getAuthentication() ).thenReturn( new AnonAuthentication() );
	}

	@After
	public final void afterAbstractTest() {
		SecurityContextHolder.clearContext();
	}

	protected SecurityContext securityContext;

	protected void mockAuthenticatedUser( User user ) {
		mockAuthentication( new TokenBasedAuthentication( user ) );
	}

	private void mockAuthentication( TokenBasedAuthentication auth ) {
		auth.setAuthenticated( true );
		Mockito.when( securityContext.getAuthentication() ).thenReturn( auth );
	}

    protected User buildTestAnonUser() {
        User user = new User();
        user.setUsername("user");
        return user;
    }

	protected User buildTestUser() {

		User user = new User();
		Authority userAuthority = new Authority();
		userAuthority.setName("ROLE_USER");
		Set<Authority> userAuthorities = new HashSet<>();
		userAuthorities.add(userAuthority);
		user.setUsername("user");
		user.setAuthorities(userAuthorities);
		return user;
	}


    protected User buildTestAdmin() {
        Authority userAuthority = new Authority();
        Authority adminAuthority = new Authority();
        userAuthority.setName("ROLE_USER");
        adminAuthority.setName("ROLE_ADMIN");
		Set<Authority> adminAuthorities = new HashSet<>();
        adminAuthorities.add(userAuthority);
        adminAuthorities.add(adminAuthority);
        User admin = new User();
        admin.setUsername("admin");
        admin.setAuthorities(adminAuthorities);
        return admin;
    }


}