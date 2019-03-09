package com.enlanguage.system.server.model.dto;


public class BoxWordDTO {

  private String baseBoXword;
  private String baseBoxWordLang;
  private String meaningBoxWord;
  private String meaningBoxWordLang;

  public BoxWordDTO() {
  }

  public String getBaseBoXword() {
    return baseBoXword;
  }

  public void setBaseBoXword(String baseBoXword) {
    this.baseBoXword = baseBoXword;
  }

  public String getBaseBoxWordLang() {
    return baseBoxWordLang;
  }

  public void setBaseBoxWordLang(String baseBoxWordLang) {
    this.baseBoxWordLang = baseBoxWordLang;
  }

  public String getMeaningBoxWord() {
    return meaningBoxWord;
  }

  public void setMeaningBoxWord(String meaningBoxWord) {
    this.meaningBoxWord = meaningBoxWord;
  }

  public String getMeaningBoxWordLang() {
    return meaningBoxWordLang;
  }

  public void setMeaningBoxWordLang(String meaningBoxWordLang) {
    this.meaningBoxWordLang = meaningBoxWordLang;
  }
}
