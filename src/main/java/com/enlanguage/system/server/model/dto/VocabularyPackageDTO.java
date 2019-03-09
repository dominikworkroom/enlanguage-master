package com.enlanguage.system.server.model.dto;

/**
 * Created by Dominik on 20.04.2017.
 */

public class VocabularyPackageDTO {

  private long id;
  private String baseWord;
  private String meaningWord;
  private String date;
  private String baseWordLang;
  private String meaningWordLang;

  public VocabularyPackageDTO(String baseWord, String meaningWord, String date, String baseWordLang,
      String meaningWordLang) {
    this.baseWord = baseWord;
    this.meaningWord = meaningWord;
    this.date = date;
    this.baseWordLang = baseWordLang;
    this.meaningWordLang = meaningWordLang;
  }

  public VocabularyPackageDTO(String baseWord, String meaningWord) {
    this.baseWord = baseWord;
    this.meaningWord = meaningWord;
  }

  public VocabularyPackageDTO() {
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public String getBaseWord() {
    return baseWord;
  }

  public void setBaseWord(String baseWord) {
    this.baseWord = baseWord;
  }

  public String getMeaningWord() {
    return meaningWord;
  }

  public void setMeaningWord(String meaningWord) {
    this.meaningWord = meaningWord;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getBaseWordLang() {
    return baseWordLang;
  }

  public void setBaseWordLang(String baseWordLang) {
    this.baseWordLang = baseWordLang;
  }

  public String getMeaningWordLang() {
    return meaningWordLang;
  }

  public void setMeaningWordLang(String meaningWordLang) {
    this.meaningWordLang = meaningWordLang;
  }
}
