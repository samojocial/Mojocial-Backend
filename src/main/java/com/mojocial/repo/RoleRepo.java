package com.mojocial.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mojocial.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	public Role findRoleByName(String name);

}
