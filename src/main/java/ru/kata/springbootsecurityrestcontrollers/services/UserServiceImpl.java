package ru.kata.springbootsecurityrestcontrollers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.springbootsecurityrestcontrollers.dao.UserRepository;
import ru.kata.springbootsecurityrestcontrollers.models.Role;
import ru.kata.springbootsecurityrestcontrollers.models.User;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Autowired
    @Lazy
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id).get();
    }

    public Role getRole(String role) {
        return roleService.findByName(role);
    }

    @Override
    public void update(Long id, User user) {
        User oldUser = userRepository.findById(id).get();
        oldUser.setUsername(user.getUsername());
        oldUser.setLastName(user.getLastName());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setRoles(user.getRoles());
        userRepository.save(oldUser);
    }

    @Override
    public User oneUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public User createUser(User user, Set<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roleSet = new HashSet<>();
        for (Role role : roles) {
            Role newRole = role;
            if (newRole != null) {
                roleSet.add(newRole);
            }
        }
        user.setRoles(roleSet);
        return user;
    }

    @Override
    public User updateUser(User user, Set<Role> roles, Long id) {
        User oldUser = getOne(id);
        String oldPassword = oldUser.getPassword();
        String newPassword = user.getPassword();

        if (newPassword != null && !newPassword.isEmpty() && !passwordEncoder.matches(newPassword, oldPassword)) {

            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            user.setPassword(oldPassword);
        }

        Set<Role> roleSet = new HashSet<>();

        if (roles == null || roles.isEmpty()) {
            roleSet = oldUser.getRoles();
        } else {
            for (Role role : roles) {
                Role newRole = role;
                if (newRole != null) {
                    roleSet.add(newRole);
                }
            }
        }
        user.setRoles(roleSet);
        return user;
    }
}
