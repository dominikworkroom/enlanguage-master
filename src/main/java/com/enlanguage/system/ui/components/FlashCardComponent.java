package com.enlanguage.system.ui.components;

import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import java.util.List;


public class FlashCardComponent extends Window {


  private final String caption = "Tap on the screen if you want shift the side.";
  private VerticalLayout center;
  private VerticalLayout leftButton;
  private VerticalLayout rightButton;
  private HorizontalLayout baseLayout;
  private String baseVocabulary;
  private String meaningVocabulary;
  private boolean whichSide;
  private Label label;
  private List<VocabularyPackageDTO> vocabularyPackage;


  public FlashCardComponent(String baseVocabulary, String meaningVocabulary,
      List<VocabularyPackageDTO> vocabularyPackage) {
    this.baseVocabulary = baseVocabulary;
    this.meaningVocabulary = meaningVocabulary;
    this.vocabularyPackage = vocabularyPackage;
    init();
    carousel();
  }

  public void init() {
    label = new Label(flashCardPattern(baseVocabulary), ContentMode.HTML);
    Label leftArrow = new Label("<h2> < </h2>", ContentMode.HTML);
    Label rightArrow = new Label("<h2> > </h2>", ContentMode.HTML);
    baseLayout = new HorizontalLayout();
    leftButton = new VerticalLayout(leftArrow);
    leftButton.setComponentAlignment(leftArrow, Alignment.MIDDLE_LEFT);
    rightButton = new VerticalLayout(rightArrow);
    rightButton.setComponentAlignment(rightArrow, Alignment.MIDDLE_RIGHT);

    setCaption(caption);
    setWidth(500.0f, Unit.PIXELS);
    center = new VerticalLayout();
    center.setWidth("300px");
    center.addComponent(label);
    center.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    center.setResponsive(true);
    center();
    shift();
    setResizable(false);
    setModal(true);

    baseLayout.addComponents(leftButton, center, rightButton);
    baseLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

    setContent(baseLayout);

  }

  private void shift() {
    center.addLayoutClickListener((LayoutEvents.LayoutClickListener) event -> {
      if (whichSide) {
        label.setValue(flashCardPattern(baseVocabulary));
        whichSide = false;
      } else {
        label.setValue(flashCardPattern(meaningVocabulary));
        whichSide = true;
      }
    });
  }

  private void carousel() {
    rightClick();
    leftClick();
  }

  private void leftClick() {
    leftButton.addLayoutClickListener((LayoutEvents.LayoutClickListener) click -> {
      for (VocabularyPackageDTO vp : vocabularyPackage) {
        String meaningWordFromPackage = vp.getMeaningWord();
        String baseWordFromPackage = vp.getBaseWord();

        if (meaningVocabulary.equals(meaningWordFromPackage) || baseVocabulary
            .equals(baseWordFromPackage)) {

          int itemIndex = vocabularyPackage.indexOf(vp);
          goLeft(itemIndex, vocabularyPackage.size());
          break;
        }
      }
    });
  }

  private void goLeft(int itemIndex, int size) {
    int nextIndex = --itemIndex;

    if (nextIndex >= 0) {
      setItemWithIndex(nextIndex);
    } else {
      setItemWithIndex(size - 1);
    }
  }

  private void rightClick() {
    rightButton.addLayoutClickListener((LayoutEvents.LayoutClickListener) click -> {
      for (VocabularyPackageDTO vp : vocabularyPackage) {
        String meaningWordFromPackage = vp.getMeaningWord();
        String baseWordFromPackage = vp.getBaseWord();

        if (meaningVocabulary.equals(meaningWordFromPackage) || baseVocabulary
            .equals(baseWordFromPackage)) {

          int itemIndex = vocabularyPackage.indexOf(vp);
          goRight(itemIndex, vocabularyPackage.size());
          break;
        }
      }
    });
  }

  private void goRight(int itemIndex, int vocabularyPackageSize) {
    int nextIndex = ++itemIndex;

    if (nextIndex <= vocabularyPackageSize - 1) {
      setItemWithIndex(nextIndex);
    } else {
      setItemWithIndex(0);
    }
  }

  private void setItemWithIndex(int i) {
    VocabularyPackageDTO vocabularyPackageDTO = vocabularyPackage.get(i);
    setMeaningVocabulary(vocabularyPackageDTO.getMeaningWord());
    setBaseVocabulary(vocabularyPackageDTO.getBaseWord());
    label.setValue(flashCardPattern(baseVocabulary));
  }


  public String flashCardPattern(String vocabularyToDisplay) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<b><h1>");
    stringBuilder.append(vocabularyToDisplay);
    stringBuilder.append("</h1></b>");

    return stringBuilder.toString();
  }


  public void setBaseVocabulary(String baseVocabulary) {
    this.baseVocabulary = baseVocabulary;
  }

  public void setMeaningVocabulary(String meaningVocabulary) {
    this.meaningVocabulary = meaningVocabulary;
  }
}
