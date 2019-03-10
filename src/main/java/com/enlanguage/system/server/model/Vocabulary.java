package com.enlanguage.system.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dominik on 27.04.2017.
 */
@Entity
@Table(name = "vocabularies")
public class Vocabulary implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "base_word")
  private String baseWord;
  @Column(name = "base_word_lang")
  private String baseWordLang;
  @NotNull
  @Column(name = "meaning_word")
  private String meaningWord;
  @Column(name = "meaning_word_lang")
  private String meaningWordLang;
  @Column(name = "created_at")
  private Date createdAt = new Date();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Vocabulary(String baseWord, String baseWordLang, String meaningWord,
      String meaningWordLang, Date createdAt) {
    this.baseWord = baseWord;
    this.baseWordLang = baseWordLang;
    this.meaningWord = meaningWord;
    this.meaningWordLang = meaningWordLang;
    this.createdAt = createdAt;
  }

  public Vocabulary() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBaseWord() {
    return baseWord;
  }

  public void setBaseWord(String baseWord) {
    this.baseWord = baseWord;
  }

  public String getBaseWordLang() {
    return baseWordLang;
  }

  public void setBaseWordLang(String baseWordLang) {
    this.baseWordLang = baseWordLang;
  }

  public String getMeaningWord() {
    return meaningWord;
  }

  public void setMeaningWord(String meaningWord) {
    this.meaningWord = meaningWord;
  }

  public String getMeaningWordLang() {
    return meaningWordLang;
  }

  public void setMeaningWordLang(String meaningWordLang) {
    this.meaningWordLang = meaningWordLang;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
