package com.enlanguage.system.ui.form;

import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.enlanguage.system.server.utils.validator.TextFieldValidator;
import com.vaadin.data.Binder;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dominik on 19.04.2017.
 */
@SpringComponent
@ViewScope
public class VocabularyForm extends FormLayout {

  private TextField baseWord;
  private TextField meaningWord;
  private Button saveButton;
  private Button resetButton;
  private HorizontalLayout buttonLayout;
  private Binder<VocabularyPackageDTO> binder = new Binder<>();
  private TextFieldValidator textFieldValidator = new TextFieldValidator();
  private Label title = new Label();

  public VocabularyForm() {
    initComponents();
    setValid();
    bindingData();
  }


  public void initComponents() {
    title.setValue("<h4><b>VOCABULARY FORM</b></h4>");
    title.setContentMode(ContentMode.HTML);
    meaningWord = new TextField("PL ");
    baseWord = new TextField("ENG ");
    saveButton = new Button("Save");
    saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
    resetButton = new Button("Reset");
    resetButton.addStyleName(ValoTheme.BUTTON_DANGER);
    buttonLayout = new HorizontalLayout(saveButton, resetButton);
    addComponents(title, baseWord, meaningWord, buttonLayout);
  }

  public void setValid() {
    meaningWord.setRequiredIndicatorVisible(true);
    baseWord.setRequiredIndicatorVisible(true);

  }

  public void bindingData() {
    binder.forField(meaningWord).withValidator(textFieldValidator)
        .bind(VocabularyPackageDTO::getMeaningWord, VocabularyPackageDTO::setMeaningWord);
    binder.forField(baseWord).withValidator(textFieldValidator)
        .bind(VocabularyPackageDTO::getBaseWord, VocabularyPackageDTO::setBaseWord);
  }

  public void clearFields() {
    textFieldValidator.setStopValidation(true);
    meaningWord.clear();
    textFieldValidator.setStopValidation(true);
    baseWord.clear();
  }


  public TextField getBaseWord() {
    return baseWord;
  }

  public void setBaseWord(TextField baseWord) {
    this.baseWord = baseWord;
  }

  public TextField getMeaningWord() {
    return meaningWord;
  }

  public void setMeaningWord(TextField meaningWord) {
    this.meaningWord = meaningWord;
  }

  public Button getSaveButton() {
    return saveButton;
  }

  public void setSaveButton(Button saveButton) {
    this.saveButton = saveButton;
  }

  public Button getResetButton() {
    return resetButton;
  }

  public void setResetButton(Button resetButton) {
    this.resetButton = resetButton;
  }

  public boolean isFieldValid() {
    return binder.isValid();
  }

  public Binder<VocabularyPackageDTO> getBinder() {
    return binder;
  }
}
