package com.enlanguage.system.ui.presenter;

import com.enlanguage.system.server.model.dto.NoteDTO;
import com.enlanguage.system.server.service.NoteService;
import com.enlanguage.system.ui.components.NoteTileComponent;
import com.enlanguage.system.ui.view.NoteView;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@SpringComponent
public class NotePresenter implements Presenter, Serializable {


  NoteView noteView;

  @Autowired
  private NoteService noteService;

  private Binder<NoteDTO> noteBinder;

  private boolean isEdit = false;
  private long temporaryNoteId;


  @Override
  public void bind() {

    noteBinder = noteView.getNoteForm().getBinder();

    noteView.getNoteForm().getSaveNoteButton().addClickListener((Button.ClickListener) event -> {
      saveOrEditNote();
    });

    noteView.getNoteForm().getDeleteNoteButton().addClickListener((Button.ClickListener) event -> {
      noteView.getNoteForm().close();
    });

    loadNotes();
  }

  private void saveOrEditNote() {

    if (noteBinder.isValid()) {
      checkIfIsEditMode();
    } else {
      noteBinder.validate();
    }
  }

  private void checkIfIsEditMode() {

    if (isEdit) {
      editNote();
    } else {
      saveNote();
    }
  }

  private void saveNote() {
    noteService
        .saveNote(writeBeanFromBinder(noteBinder), () -> noteView.getNoteForm().clearFields());
    Notification.show("Data added");
    noteView.getNoteForm().close();
    loadNotes();
  }

  private void editNote() {

    NoteDTO noteDTOForEdit = writeBeanFromBinder(noteBinder);
    noteDTOForEdit.setId(temporaryNoteId);

    noteService.editNote(noteDTOForEdit, () -> noteView.getNoteForm().clearFields());
    Notification.show("Data edited");
    noteView.getNoteForm().close();
    loadNotes();
  }

  public NoteDTO writeBeanFromBinder(Binder<NoteDTO> binder) {

    NoteDTO noteDTO = new NoteDTO();

    try {
      binder.writeBean(noteDTO);
    } catch (ValidationException e) {
      Notification
          .show("Write binder error in: " + getClass().getName(), Notification.Type.ERROR_MESSAGE);

    }

    return noteDTO;
  }

  /**
   * For each loaded note, init delete and edit logic
   */
  public void loadNotes() {
    List<NoteDTO> noteDTOS = noteService.loadNotes();
    noteView.getNoteTileRow().removeAllComponents();

    noteDTOS.forEach(e -> {
      NoteTileComponent noteTileComponent = new NoteTileComponent(e);
      noteTileComponent.getDeleteItem().addClickListener(
          (Button.ClickListener) action -> UI.getCurrent()
              .addWindow(deleteNote(noteTileComponent)));

      noteTileComponent.getEditItem().addClickListener((Button.ClickListener) edit -> {
        isEdit = true;
        UI.getCurrent().addWindow(noteView.getNoteForm());
        noteBinder.readBean(e);
        temporaryNoteId = noteTileComponent.getNoteId();

      });

      noteView.getNoteTileRow().addComponent(noteTileComponent);

    });
  }


  public void setNoteView(NoteView noteView) {
    this.noteView = noteView;
  }

  public Window deleteNote(NoteTileComponent noteTileComponent) {
    Window confirm = new Window();
    confirm.setCaption("Confirm message");
    confirm.center();
    confirm.setModal(true);

    Button accept = new Button("Yes");
    accept.addClickListener((Button.ClickListener) a -> {
      noteService.deleteNote(noteTileComponent.getNoteId());
      loadNotes();
      confirm.close();
    });

    Button cancel = new Button("No");
    cancel.addClickListener((Button.ClickListener) n -> confirm.close());

    VerticalLayout message = new VerticalLayout(
        new Label("<b> Do you wanna delete this note? </b>", ContentMode.HTML));
    HorizontalLayout buttonRow = new HorizontalLayout(accept, cancel);
    VerticalLayout container = new VerticalLayout(message, buttonRow);
    container.setComponentAlignment(buttonRow, Alignment.TOP_CENTER);

    confirm.setContent(container);

    return confirm;
  }

  public void setEdit(boolean edit) {
    isEdit = edit;
  }
}
