package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.Note;
import com.enlanguage.system.server.model.dto.NoteDTO;
import com.enlanguage.system.server.utils.TextFieldClear;

import java.util.List;


public interface NoteService {

  void saveNote(NoteDTO noteDTO, TextFieldClear textFieldClear);

  List<NoteDTO> loadNotes();

  void editNote(NoteDTO noteDTO, TextFieldClear textFieldClear);

  void deleteNote(long id);
}
