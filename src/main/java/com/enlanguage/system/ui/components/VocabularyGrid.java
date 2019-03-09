package com.enlanguage.system.ui.components;

import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;

/**
 * Created by Dominik on 20.04.2017.
 */


@SpringComponent
@ViewScope
public class VocabularyGrid extends VerticalLayout {

  private Grid<VocabularyPackageDTO> grid;
  private Button deleteButton;
  private TextField searchInput;
  private Button searchButton;
  private HorizontalLayout buttons;
  private Label title;
  private Button addToRememboxButton;

  public VocabularyGrid() {
    initGrid();
  }

  public void initGrid() {
    title = new Label();
    title.setValue("<h4><b>MY DICTIONARY</b></h4>");
    title.setContentMode(ContentMode.HTML);
    buttons = new HorizontalLayout();
    grid = new Grid<>();
    deleteButton = new Button("Delete vocabulary");
    addToRememboxButton = new Button("Add to Remembox");
    searchInput = new TextField();
    searchInput.setWidth("500px");
    searchInput.setPlaceholder(
        "Search by either word or meaning or click search button without any value to get all...");
    searchButton = new Button("Search");
    grid.addColumn(VocabularyPackageDTO::getMeaningWord).setCaption("Word");
    grid.addColumn(VocabularyPackageDTO::getMeaningWordLang).setCaption("Language");
    grid.addColumn(VocabularyPackageDTO::getBaseWord).setCaption("Meaning");
    grid.addColumn(VocabularyPackageDTO::getBaseWordLang).setCaption("Language");
    grid.addColumn(VocabularyPackageDTO::getDate).setCaption("Data");
    grid.setSizeUndefined();

    grid.setWidth("1000px");
    grid.setHeight("450px");
    buttons.addComponents(deleteButton, addToRememboxButton, searchInput, searchButton);
    addComponents(title, buttons, grid);
  }


  public Grid<VocabularyPackageDTO> getGrid() {
    return grid;
  }

  public void setGrid(Grid<VocabularyPackageDTO> grid) {
    this.grid = grid;
  }

  public Button getDeleteButton() {
    return deleteButton;
  }

  public TextField getSearchInput() {
    return searchInput;
  }

  public void setSearchInput(TextField searchInput) {
    this.searchInput = searchInput;
  }

  public Button getSearchButton() {
    return searchButton;
  }

  public void setSearchButton(Button searchButton) {
    this.searchButton = searchButton;
  }

  public Button getAddToRememboxButton() {
    return addToRememboxButton;
  }

  public void setAddToRememboxButton(Button addToRememboxButton) {
    this.addToRememboxButton = addToRememboxButton;
  }
}
