package com.enlanguage.system.ui.view;

import com.enlanguage.system.EnlanguageUI;
import com.enlanguage.system.ui.components.RememboxComponent;
import com.enlanguage.system.ui.form.VocabularyForm;
import com.enlanguage.system.ui.components.VocabularyGrid;
import com.enlanguage.system.ui.presenter.ManageVocabularyPresenter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;


/**
 * Created by Dominik on 19.04.2017.
 */
@SpringView(name = MainView.MAIN_VIEW_NAME, ui = EnlanguageUI.class)
@SpringComponent
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class MainView extends VerticalLayout implements View {

  public final static String MAIN_VIEW_NAME = "";

  private VocabularyForm vocabularyForm;
  private VocabularyGrid vocabularyGrid;
  private ManageVocabularyPresenter manageVocabularyPresenter;
  private RememboxComponent rememboxComponent;
  private HorizontalLayout horizontalLayout = new HorizontalLayout();
  private VerticalLayout verticalLayout = new VerticalLayout();

  @PostConstruct
  private void initContent() {
    verticalLayout.addComponents(rememboxComponent, vocabularyGrid);
    horizontalLayout.addComponents(vocabularyForm, verticalLayout);
    addComponents(horizontalLayout);
    initPresenter();
  }

  @Autowired
  MainView(VocabularyGrid vocabularyGrid, VocabularyForm vocabularyForm
      , ManageVocabularyPresenter manageVocabularyPresenter, RememboxComponent rememboxComponent) {
    super();
    this.vocabularyGrid = vocabularyGrid;
    this.vocabularyForm = vocabularyForm;
    this.manageVocabularyPresenter = manageVocabularyPresenter;
    this.rememboxComponent = rememboxComponent;
  }

  private void initPresenter() {
    manageVocabularyPresenter.setMainView(this);
    manageVocabularyPresenter.bind();
  }


  @Override
  public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
  }

  public VocabularyForm getVocabularyForm() {
    return vocabularyForm;
  }

  public VocabularyGrid getVocabularyGrid() {
    return vocabularyGrid;
  }

  public RememboxComponent getRememboxComponent() {
    return rememboxComponent;
  }

}
