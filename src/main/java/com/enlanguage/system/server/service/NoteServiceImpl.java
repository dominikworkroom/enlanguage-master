package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.Note;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.dto.NoteDTO;
import com.enlanguage.system.server.repository.NoteRepository;
import com.enlanguage.system.server.repository.UserRepository;
import com.enlanguage.system.server.utils.TextFieldClear;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {


  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void saveNote(NoteDTO noteDTO, TextFieldClear textFieldClear) {

    if (noteDTO == null) {
      Notification.show("Incorrect data.", Notification.Type.ERROR_MESSAGE);
      return;
    }

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findByUsername(auth.getName());

    Note note = new Note(noteDTO);
    note.setUser(user);

    noteRepository.save(note);

    textFieldClear.clearFields();

  }

  @Override
  public List<NoteDTO> loadNotes() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findByUsername(auth.getName());

    try {
      List<NoteDTO> notes = new ArrayList<>();
      List<Note> all = noteRepository.findAllByUser(user);

      all.forEach(e -> notes.add(new NoteDTO(e)));

      return notes;

    } catch (Exception e) {
      Notification.show("Loading data error", Notification.Type.ERROR_MESSAGE);
    }

    return Collections.emptyList();
  }

  @Override
  public void editNote(NoteDTO noteDTO, TextFieldClear textFieldClear) {
    if (noteDTO == null) {
      Notification.show("Incorrect data.", Notification.Type.ERROR_MESSAGE);
      return;
    }

    Note note = noteRepository.findById(noteDTO.getId());
    note.setDescription(noteDTO.getDescription());
    note.setTheme(noteDTO.getTheme());
    note.setCreatedAt(new Date());
    noteRepository.save(note);


  }

  @Override
  public void deleteNote(long id) {
    noteRepository.delete(id);
  }

}
