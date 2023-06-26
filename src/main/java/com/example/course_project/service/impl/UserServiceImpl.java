package com.example.course_project.service.impl;

import com.example.course_project.entity.Role;
import com.example.course_project.entity.User;
import com.example.course_project.repository.RoleRepository;
import com.example.course_project.repository.UserRepository;
import com.example.course_project.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String password, String email, String lastName, String firstName) {
        User user = findUserByEmail(email);
        if (user != null) throw new RuntimeException("User with email :" + email + "already exist");
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.save(new User(encodedPassword, email, lastName, firstName));
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User with id " + userId + " Not Found"));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = findUserByEmail(email);
        Role role = roleRepository.getRoleByRoleName(roleName);
        user.assignRoleToUser(role);
    }

    @Override
    public User createOrUpdateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(String email, String roleName) {
        User user = findUserByEmail(email);
        Role role = roleRepository.getRoleByRoleName(roleName);
        user.removeRoleFromUser(role);
    }

    @Override
    public boolean doesCurrentUserHasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
