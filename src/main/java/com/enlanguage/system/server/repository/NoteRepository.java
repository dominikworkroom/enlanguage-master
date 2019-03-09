package com.enlanguage.system.server.repository;


import com.enlanguage.system.server.model.Note;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.dto.NoteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  List<Note> findAllByUser(User user);

  Note findById(long id);
}
