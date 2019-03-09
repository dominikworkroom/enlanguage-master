package com.enlanguage.system.ui.view;


import com.enlanguage.system.EnlanguageUI;

import com.enlanguage.system.ui.form.NoteForm;
import com.enlanguage.system.ui.presenter.NotePresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;


@SpringView(name = NoteView.GRAMMAR_VIEW_NAME, ui = EnlanguageUI.class)
@SpringComponent
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class NoteView extends VerticalLayout implements View {

  public static final String GRAMMAR_VIEW_NAME = "note";
  private NoteForm noteForm;

  private NotePresenter notePresenter;
  private VerticalLayout noteTileRow;


  @PostConstruct
  public void initComponents() {
    Button newNote = new Button("New Note");
    newNote.setIcon(new ThemeResource("img/add.png"));
    newNote.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT);

    HorizontalLayout buttonContainer = new HorizontalLayout();
    noteTileRow = new VerticalLayout();

    addComponents(buttonContainer, noteTileRow);
    newNote.addClickListener((Button.ClickListener) event -> {
      notePresenter.setEdit(false);
      noteForm.clearFields();
      UI.getCurrent().addWindow(noteForm);
    });

    buttonContainer.addComponents(newNote);
    initPresenter();
  }


  @Autowired
  public NoteView(NoteForm noteForm, NotePresenter notePresenter) {
    this.noteForm = noteForm;
    this.notePresenter = notePresenter;
  }


  public void initPresenter() {
    notePresenter.setNoteView(this);
    notePresenter.bind();

  }

  public VerticalLayout getNoteTileRow() {
    return noteTileRow;
  }


  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

  }

  public NoteForm getNoteForm() {
    return noteForm;
  }


}
