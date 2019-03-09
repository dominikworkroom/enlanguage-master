package com.enlanguage.system.ui.presenter;

import com.enlanguage.system.server.model.dto.BoxWordDTO;
import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.enlanguage.system.server.service.ManageVocabularyService;
import com.enlanguage.system.ui.components.FlashCardComponent;
import com.enlanguage.system.ui.components.RememboxComponent;
import com.enlanguage.system.ui.form.VocabularyForm;
import com.enlanguage.system.ui.view.MainView;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Dominik on 03.05.2017.
 */

@SpringComponent
public class ManageVocabularyPresenter implements Presenter, Serializable {

  private MainView mainView;
  private Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  private ManageVocabularyService manageVocabularyService;

  @Override
  public void bind() {
    VocabularyForm vocabularyForm = mainView.getVocabularyForm();
    Grid<VocabularyPackageDTO> grid = mainView.getVocabularyGrid().getGrid();
    Binder<VocabularyPackageDTO> addVocabularyFormBinder = mainView.getVocabularyForm().getBinder();
    RememboxComponent rememboxComponent = mainView.getRememboxComponent();

    initData(rememboxComponent);

    vocabularyForm.getSaveButton().addClickListener((Button.ClickListener) clickEvent -> {
      saveVocabulary(vocabularyForm, grid, addVocabularyFormBinder);
    });

    mainView.getVocabularyForm().getBaseWord().addShortcutListener(
        new ShortcutListener("Base Vocabulary Shortcut", ShortcutAction.KeyCode.ENTER, null) {
          @Override
          public void handleAction(Object sender, Object target) {
            saveVocabulary(vocabularyForm, grid, addVocabularyFormBinder);
          }
        });

    mainView.getVocabularyForm().getMeaningWord().addShortcutListener(
        new ShortcutListener("Meaning Vocabulary Shortcut", ShortcutAction.KeyCode.ENTER, null) {
          @Override
          public void handleAction(Object sender, Object target) {
            saveVocabulary(vocabularyForm, grid, addVocabularyFormBinder);
          }
        });

    mainView.getVocabularyGrid().getDeleteButton()
        .addClickListener((Button.ClickListener) event -> {
          Set<VocabularyPackageDTO> selectedItems = grid.getSelectedItems();

          selectedItems.forEach(e -> {
            manageVocabularyService.deleteVocabulary(e);
            transferDataToGrid(grid, manageVocabularyService.loadVocabulary());
          });

        });

    mainView.getVocabularyForm().getResetButton().addClickListener((Button.ClickListener) event -> {
      vocabularyForm.clearFields();
    });

    mainView.getVocabularyGrid().getGrid()
        .addItemClickListener((ItemClickListener<VocabularyPackageDTO>) event -> {
          VocabularyPackageDTO item = event.getItem();
          if (event.getMouseEventDetails().isDoubleClick()) {
            Collection<Window> windows = UI.getCurrent().getWindows();

            try {
              if (windows.size() > 0) {
                windows.clear();
              } else {
                FlashCardComponent flashCardComponent = new FlashCardComponent(item.getBaseWord(),
                    item.getMeaningWord(), manageVocabularyService.loadVocabulary());
                UI.getCurrent().addWindow(flashCardComponent);
              }
            } catch (Exception e) {
              logger.log(Level.WARNING, "Unsupported Operation", e);

            }

          }
        });

    mainView.getVocabularyGrid().getSearchButton()
        .addClickListener((Button.ClickListener) event -> {
          String searchValue = mainView.getVocabularyGrid().getSearchInput().getValue();
          if (searchValue == null || searchValue.isEmpty()) {
            logger.log(Level.WARNING, "Search value is empty");
            transferDataToGrid(grid, manageVocabularyService.loadVocabulary());
          } else {
            List<VocabularyPackageDTO> searchResult = manageVocabularyService
                .searchVocabulary(searchValue);
            transferDataToGrid(grid, searchResult);
          }
        });

    rememboxComponent.getSaveNumberOfRememberWords()
        .addClickListener((Button.ClickListener) event -> {
          String value = rememboxComponent.getNumberOfRememberWords().getValue();
          if (StringUtils.isNumeric(value)) {
            manageVocabularyService.saveNumberOfRememberWords(value);
            Notification.show("Number of remember words has been saved!");
          } else {
            Notification
                .show("Number of remember words has to be a numeric type.", Type.WARNING_MESSAGE);
          }
        });

    mainView.getVocabularyGrid().getAddToRememboxButton()
        .addClickListener((Button.ClickListener) event -> {
          Set<VocabularyPackageDTO> selectedItems = grid.getSelectedItems();
          if (selectedItems != null && selectedItems.size() > 0) {
            manageVocabularyService.addRememberBoxWords(selectedItems);
            rememboxComponent.getRememboxGrid()
                .setItems(manageVocabularyService.loadRememberWords());
          } else {
            Notification.show("Vocabulary has to be selected.", Type.WARNING_MESSAGE);
          }
        });

    rememboxComponent.getDeleteButton().addClickListener((Button.ClickListener) event -> {
      final Set<BoxWordDTO> selectedItems = rememboxComponent.getRememboxGrid().getSelectedItems();
      if (selectedItems != null && selectedItems.size() > 0) {
        manageVocabularyService.deleteRememberWord(selectedItems);
        rememboxComponent.getRememboxGrid().setItems(manageVocabularyService.loadRememberWords());
        Notification.show("Remember word has been deleted correctly.");
      } else {
        Notification.show("Remember word has to be selected.", Type.WARNING_MESSAGE);
      }
    });

    transferDataToGrid(grid, manageVocabularyService.loadVocabulary());
  }

  private void initData(RememboxComponent rememboxComponent) {
    //initial number of remember words
    rememboxComponent.getNumberOfRememberWords()
        .setValue(String.valueOf(manageVocabularyService.getNumberOfRememberWords()));
    rememboxComponent.getRememboxGrid().setItems(manageVocabularyService.loadRememberWords());

  }

  public void saveVocabulary(VocabularyForm vocabularyForm, Grid<VocabularyPackageDTO> grid,
      Binder<VocabularyPackageDTO> binder) {

    VocabularyPackageDTO wordPackage = bindData(binder);

    if (binder.isValid()) {
      manageVocabularyService.saveVocabulary(wordPackage, () -> vocabularyForm.clearFields());
      Notification.show("Dane zostały zapisane");
      transferDataToGrid(grid, manageVocabularyService.loadVocabulary());
    } else {
      binder.validate();
    }
  }


  public void transferDataToGrid(Grid<VocabularyPackageDTO> grid,
      List<VocabularyPackageDTO> vocabularyPackageDTOS) {
    grid.setItems(vocabularyPackageDTOS);
  }

  public VocabularyPackageDTO bindData(Binder<VocabularyPackageDTO> binder) {
    VocabularyPackageDTO wordPackage = new VocabularyPackageDTO();

    try {
      binder.writeBean(wordPackage);
    } catch (ValidationException e) {
      e.printStackTrace();
    }

    return wordPackage;
  }


  public void setMainView(MainView mainView) {
    this.mainView = mainView;
  }
}
