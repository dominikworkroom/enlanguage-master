package com.enlanguage.system.server.repository;

import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.dto.NoteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

}
