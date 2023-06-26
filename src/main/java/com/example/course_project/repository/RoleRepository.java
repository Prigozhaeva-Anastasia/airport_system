package com.example.course_project.repository;

import com.example.course_project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByRoleName(String roleName);
}
