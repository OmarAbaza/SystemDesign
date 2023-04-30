package com.test.systemdesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.systemdesign.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
