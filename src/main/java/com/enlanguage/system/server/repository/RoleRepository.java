package com.enlanguage.system.server.repository;

import com.enlanguage.system.server.model.Role;
import com.enlanguage.system.server.model.enumeration.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role, Long> {

  List<Role> findByUserId(Long userId);
}
