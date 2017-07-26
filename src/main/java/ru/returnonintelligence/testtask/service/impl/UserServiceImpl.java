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
import ru.returnonintelligence.testtask.model.Group;
import ru.returnonintelligence.testtask.model.User;
import ru.returnonintelligence.testtask.repository.UserRepository;
import ru.returnonintelligence.testtask.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

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
        Set<Authority> adminAuthorities = new HashSet<>();
        Set<Authority> userAuthorities = new HashSet<>();
        userAuthorities.add(userAuthority1);
        adminAuthorities.add(userAuthority);
        adminAuthorities.add(adminAuthority);

        Group group1 = new Group();
        group1.setName("managers");
        Group group2 = new Group();
        group2.setName("developers");
        Group group3 = new Group();
        group3.setName("testers");
        Set<Group> groups1 = new HashSet<>();
        groups1.add(group1);
        groups1.add(group2);
        Set<Group> groups2 = new HashSet<>();
        groups2.add(group3);
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
                .email("admin@admin.com")
                .birthday(LocalDate.parse("1997-09-20"))
                .address(address)
                .groups(groups2)
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
                .email("user@user.com")
                .birthday(LocalDate.parse("1996-09-20"))
                .address(address1)
                .groups(groups1)
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
    @PreAuthorize("hasRole('ADMIN')")
    public void setAll(List<User> userList) {
        userRepository.save(userList);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> getById(Long id) throws AccessDeniedException  {
        return Optional.ofNullable(userRepository.findById(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUser(User user) {
        saveUser(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserById(long id) {
        userRepository.delete(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean isUserExist(User user) {
        return getByUsername(user.getUsername()).isPresent();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void reActivateUserByUsername(String username) {
        getByUsername(username).ifPresent((user)->{
            user.setIsActive(!user.getIsActive());
            updateUser(user);
        });
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Long countAll() {
        return userRepository.count();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<User> getAllByUsernameContainingAndIsActive(String username, boolean isActive) {
        return userRepository.findAllByUsernameContainingAndIsActive(username, isActive);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<User> getAllByIsActive(boolean isActive) {
        return userRepository.findAllByIsActive(isActive);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Optional<User> getByEmailAndIsActive(String email, boolean isActive) {
        return Optional.ofNullable(userRepository.findByEmailAndIsActive(email, isActive));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<User> getAllByBirthdayAndIsActive(LocalDate birthday, boolean isActive) {
        return userRepository.findAllByBirthdayAndIsActive(birthday,isActive);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getByUsernameContaining(String username) {
        return userRepository.findAllByUsernameContaining(username);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Optional<User> getByUsername(@NonNull String username) throws UsernameNotFoundException{
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> getByEmail(@NonNull String email) {
        return  Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllByBirthday(@NonNull LocalDate birthday) {
        return  userRepository.findAllByBirthday(birthday);
    }

}
