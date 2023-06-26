package com.example.course_project.service.impl;

import com.example.course_project.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testCreateRole() {
        roleService.createRole("Director");
        verify(roleRepository).save(any());
    }

    @Test
    void testFindRoleByName() {
        String roleName = "Director";
        roleService.findRoleByName(roleName);
        verify(roleRepository).getRoleByRoleName(roleName);
    }
}