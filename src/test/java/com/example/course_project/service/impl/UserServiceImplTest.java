package com.example.course_project.service.impl;

import com.example.course_project.entity.Role;
import com.example.course_project.entity.TransitFlight;
import com.example.course_project.entity.User;
import com.example.course_project.repository.RoleRepository;
import com.example.course_project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private User mockedUser;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @Test
    void testCreateUser() {
        userService.createUser("12345678", "user@gmail.com", "Smirnov", "Aleksey");
        verify(userRepository).save(any());
    }

    @Test
    void testFindUserByEmail() {
        String email = "user@gmail.com";
        userService.findUserByEmail(email);
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        User actualUser = userService.findUserById(1L);
        assertEquals(user, actualUser);
    }

    @Test
    void testAssignRoleToUser() {
        Role role = new Role();
        role.setId(1L);
        when(userRepository.findUserByEmail(any())).thenReturn(mockedUser);
        when(roleRepository.getRoleByRoleName(any())).thenReturn(role);
        userService.assignRoleToUser("user@gmail.com", "Director");
        verify(mockedUser).assignRoleToUser(role);
    }

    @Test
    void testCreateOrUpdateUser() {
        User user = new User();
        user.setId(1L);
        userService.createOrUpdateUser(user);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedValue = argumentCaptor.getValue();
        assertEquals(user, capturedValue);
    }

    @Test
    void testRemoveRoleFromUser() {
        Role role = new Role();
        role.setId(1L);
        when(userRepository.findUserByEmail(any())).thenReturn(mockedUser);
        when(roleRepository.getRoleByRoleName(any())).thenReturn(role);
        userService.removeRoleFromUser("user@gmail.com", "Director");
        verify(mockedUser).removeRoleFromUser(role);
    }
}