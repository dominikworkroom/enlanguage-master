package com.enlanguage.system.server.utils.validator;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;

/**
 * Created by Dominik on 21.05.2017.
 */
public class TextFieldValidator implements Validator<String> {

  boolean stopValidation;

  @Override
  public ValidationResult apply(String value, ValueContext context) {
    if ((value == null || value.isEmpty()) && !stopValidation) {
      return ValidationResult.error("Field is required!");
    } else {
      stopValidation = false;
      return ValidationResult.ok();
    }
  }

  public boolean isStopValidation() {
    return stopValidation;
  }

  /**
   * Stop validator, for different cases validation doesn't required. setStopValidation(true)
   * Validation off setStopValidation(false) Validation On
   */

  public void setStopValidation(boolean stopValidation) {
    this.stopValidation = stopValidation;
  }
}
