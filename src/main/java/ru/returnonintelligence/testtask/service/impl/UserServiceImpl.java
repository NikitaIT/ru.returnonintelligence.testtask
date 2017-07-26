package ru.returnonintelligence.testtask.service.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.returnonintelligence.testtask.model.Address;
import ru.returnonintelligence.testtask.model.Authority;
import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.repository.UserRepository;
import ru.returnonintelligence.testtask.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @PostConstruct
    void setStarterUsers(){
        Authority userAuthority = new Authority();
        Authority userAuthority1 = new Authority();
        Authority adminAuthority = new Authority();
        userAuthority1.setName("ROLE_USER");
        userAuthority.setName("ROLE_USER");
        adminAuthority.setName("ROLE_ADMIN");
        List<Authority> adminAuthorities = new ArrayList<>();
        List<Authority> userAuthorities = new ArrayList<>();
        userAuthorities.add(userAuthority1);
        adminAuthorities.add(userAuthority);
        adminAuthorities.add(adminAuthority);
        Address address = Address
                .builder()
                .city("Spb")
                .country("Russia")
                .district("SZ")
                .zip(12345l)
                .street("Santiago-de-Cuba")
                .build();
        User admin = User
                .builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("123"))
                .firstname("Jon")
                .lastname("Doe")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .email("admin@admin.ru")
                .birthday(LocalDate.parse("1997-09-20"))
                .address(address)
                .isActive(true)
                .authorities(adminAuthorities)
                .build();
        userRepository.save(admin);

        Address address1 = Address
                .builder()
                .city("Spb")
                .country("Russia")
                .district("SZ")
                .zip(12345l)
                .street("Santiago-de-Cuba")
                .build();
        User user = User
                .builder()
                .username("user")
                .password(new BCryptPasswordEncoder().encode("123"))
                .firstname("Nikita")
                .lastname("Fiodorov")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .email("user@user.ru")
                .birthday(LocalDate.parse("1996-09-20"))
                .address(address1)
                .isActive(true)
                .authorities(userAuthorities)
                .build();
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAll() throws AccessDeniedException {
        return  userRepository.findAll();
    }
    @Override
    public void setAll(List<User> userList) {
        userRepository.save(userList);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> getById(Long id) throws AccessDeniedException  {
        return Optional.ofNullable(userRepository.findById(id));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        saveUser(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.delete(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public boolean isUserExist(User user) {
        return getByUsername(user.getUsername()).isPresent();
    }
    @Override
    public List<User> getByUsernameContaining(String username) {
        return userRepository.findAllByUsernameContaining(username);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Optional<User> getByUsername(@NonNull String username) throws UsernameNotFoundException{
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public Optional<User> getByEmail(@NonNull String email) {
        return  Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public List<User> getAllByBirthday(@NonNull LocalDate birthday) {
        return  userRepository.findAllByBirthday(birthday);
    }

}