package com.enlanguage.system.ui.view.error;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringComponent
public class AccessDeniedView extends VerticalLayout implements View {


  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

  }

  @PostConstruct
  public void init() {
    Label label = new Label("this is AccessDeniedView.");
    label.addStyleName(ValoTheme.LABEL_FAILURE);
    addComponent(label);
  }
}
