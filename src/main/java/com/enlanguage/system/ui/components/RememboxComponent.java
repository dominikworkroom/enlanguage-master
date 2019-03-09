package com.enlanguage.system.ui.components;

import com.enlanguage.system.server.model.dto.BoxWordDTO;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import javafx.beans.property.Property;

@SpringComponent
@ViewScope
public class RememboxComponent extends VerticalLayout {

  private Grid<BoxWordDTO> rememboxGrid;
  private Button deleteButton;
  private HorizontalLayout buttons;
  private Label title;
  private Label slotTitle;
  private TextField numberOfRememberWords;
  private Button saveNumberOfRememberWords;

  public RememboxComponent() {
    initGrid();
  }

  public void initGrid() {
    buttons = new HorizontalLayout();
    rememboxGrid = new Grid<>();
    deleteButton = new Button("Delete vocabulary");
    title = new Label();
    title.setValue("<h4><b>REMEMBOX</b></h4>");
    title.setContentMode(ContentMode.HTML);
    slotTitle = new Label();
    slotTitle.setValue("Number of remember words: ");
    numberOfRememberWords = new TextField();
    numberOfRememberWords.setWidth("100px");
    numberOfRememberWords.setValue("0");
    saveNumberOfRememberWords = new Button("Save number");
    rememboxGrid.addColumn(BoxWordDTO::getBaseBoXword).setCaption("Word");
    rememboxGrid.addColumn(BoxWordDTO::getBaseBoxWordLang).setCaption("Language");
    rememboxGrid.addColumn(BoxWordDTO::getMeaningBoxWord).setCaption("Meaning");
    rememboxGrid.addColumn(BoxWordDTO::getMeaningBoxWordLang).setCaption("Language");
    rememboxGrid.setSizeUndefined();

    rememboxGrid.setWidth("1000px");
    rememboxGrid.setHeight("250px");
    buttons
        .addComponents(deleteButton, slotTitle, numberOfRememberWords, saveNumberOfRememberWords);
    addComponents(title, buttons, rememboxGrid);
  }

  public Grid<BoxWordDTO> getRememboxGrid() {
    return rememboxGrid;
  }

  public void setRememboxGrid(
      Grid<BoxWordDTO> rememboxGrid) {
    this.rememboxGrid = rememboxGrid;
  }

  public Button getDeleteButton() {
    return deleteButton;
  }

  public void setDeleteButton(Button deleteButton) {
    this.deleteButton = deleteButton;
  }

  public HorizontalLayout getButtons() {
    return buttons;
  }

  public void setButtons(HorizontalLayout buttons) {
    this.buttons = buttons;
  }

  public TextField getNumberOfRememberWords() {
    return numberOfRememberWords;
  }

  public void setNumberOfRememberWords(TextField numberOfRememberWords) {
    this.numberOfRememberWords = numberOfRememberWords;
  }

  public Button getSaveNumberOfRememberWords() {
    return saveNumberOfRememberWords;
  }

  public void setSaveNumberOfRememberWords(Button saveNumberOfRememberWords) {
    this.saveNumberOfRememberWords = saveNumberOfRememberWords;
  }
}
