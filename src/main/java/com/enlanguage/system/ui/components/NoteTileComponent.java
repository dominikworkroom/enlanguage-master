package com.enlanguage.system.ui.components;

import com.enlanguage.system.server.model.dto.NoteDTO;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class NoteTileComponent extends CssLayout {

  private long noteId;
  private NoteDTO noteDTO;
  private Button cancelPreview;
  private Button deleteItem;
  private Button editItem;

  public NoteTileComponent(NoteDTO noteDTO) {
    this.noteDTO = noteDTO;
    this.noteId = noteDTO.getId();
    init();
  }


  public void init() {
    Button previewItem = new Button();
    previewItem.setStyleName("previewButton");
    editItem = new Button();
    deleteItem = new Button();
    cancelPreview = new Button("Cancel");

    Button titleFromItem = new Button();
    titleFromItem.setCaption(noteDTO.getTheme());
    titleFromItem.setStyleName("focus");
    titleFromItem.setStyleName(ValoTheme.BUTTON_LINK);
    titleFromItem.addClickListener((Button.ClickListener) event -> displayNote());
    previewItem.addClickListener((Button.ClickListener) event -> displayNote());

    previewItem.setIcon(new ThemeResource("img/search.png"));
    previewItem.setStyleName(ValoTheme.BUTTON_LINK);
    editItem.setIcon(new ThemeResource("img/pencil.png"));
    editItem.setStyleName(ValoTheme.BUTTON_LINK);
    deleteItem.setIcon(new ThemeResource("img/garbage.png"));
    deleteItem.setStyleName(ValoTheme.BUTTON_LINK);

    addComponents(previewItem, editItem, deleteItem, titleFromItem);

  }

  public VerticalLayout initVerticalLayout(VerticalLayout verticalLayout) {

    Label titleLabel = new Label(noteDTO.getTheme(), ContentMode.HTML);
    Label descriptionLabel = new Label(noteDTO.getDescription(), ContentMode.HTML);

    Panel descriptionContentPanel = new Panel(descriptionLabel);
    descriptionContentPanel.setStyleName("notePanelMargin");
    Panel titleContentPanel = new Panel(titleLabel);
    titleContentPanel.setStyleName("notePanelMargin");

    descriptionContentPanel.setHeight(279.0f, Unit.PIXELS);

    verticalLayout.addComponents(titleContentPanel, descriptionContentPanel, cancelPreview);

    return verticalLayout;
  }

  public void displayNote() {
    Window window = new Window();
    window.center();
    window.setModal(true);
    window.setResizable(false);
    window.setCaption("Preview");
    window.setWidth(557.0f, Unit.PIXELS);
    window.setHeight(425.0f, Unit.PIXELS);

    cancelPreview.addClickListener((Button.ClickListener) action -> window.close());

    VerticalLayout verticalLayout = new VerticalLayout();

    window.setContent(initVerticalLayout(verticalLayout));
    UI.getCurrent().addWindow(window);

  }


  public Button getDeleteItem() {
    return deleteItem;
  }

  public Button getEditItem() {
    return editItem;
  }

  public NoteDTO getNoteDTO() {
    return noteDTO;
  }

  public long getNoteId() {
    return noteId;
  }

}
