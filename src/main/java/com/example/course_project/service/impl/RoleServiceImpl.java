package com.example.course_project.service.impl;
import com.example.course_project.entity.Role;
import com.example.course_project.repository.RoleRepository;
import com.example.course_project.service.RoleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.getRoleByRoleName(roleName);
    }
}
