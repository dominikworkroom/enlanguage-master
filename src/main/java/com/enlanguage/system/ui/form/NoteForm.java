package com.enlanguage.system.ui.form;

import com.enlanguage.system.server.model.dto.NoteDTO;

import com.enlanguage.system.server.utils.validator.TextFieldValidator;
import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@ViewScope
public class NoteForm extends Window {


  private TextField themeField;
  private RichTextArea description;
  private Button saveNoteButton;
  private Button deleteNoteButton;
  private CheckBox prioryty;
  private Binder<NoteDTO> binder = new Binder<>();
  private TextFieldValidator textFieldValidator = new TextFieldValidator();

  public NoteForm() {
    init();
    bindingData();

  }

  public void init() {
    themeField = new TextField("Theme");
    themeField.setWidth(498.0f, Unit.PIXELS);
    description = new RichTextArea("Description");
    saveNoteButton = new Button("Save");
    saveNoteButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
    deleteNoteButton = new Button("Cancel");
    deleteNoteButton.setStyleName(ValoTheme.BUTTON_DANGER);
    prioryty = new CheckBox("Priority");
    setWidth(557.0f, Unit.PIXELS);
    setHeight(476.0f, Unit.PIXELS);

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout(saveNoteButton, deleteNoteButton);
    verticalLayout.addComponents(themeField, description, prioryty, horizontalLayout);

    setContent(verticalLayout);

    center();

  }

  public void bindingData() {
    binder.forField(themeField).withValidator(textFieldValidator)
        .bind(NoteDTO::getTheme, NoteDTO::setTheme);
    binder.forField(description).withValidator(textFieldValidator)
        .bind(NoteDTO::getDescription, NoteDTO::setDescription);
    binder.forField(prioryty).bind(NoteDTO::isPriority, NoteDTO::setPrioryty);
  }

  public void clearFields() {
    textFieldValidator.setStopValidation(true);
    themeField.clear();
    textFieldValidator.setStopValidation(true);
    description.clear();
    textFieldValidator.setStopValidation(true);
    prioryty.clear();
  }

  public Button getSaveNoteButton() {
    return saveNoteButton;
  }

  public Button getDeleteNoteButton() {
    return deleteNoteButton;
  }

  public Binder<NoteDTO> getBinder() {
    return binder;
  }
}
