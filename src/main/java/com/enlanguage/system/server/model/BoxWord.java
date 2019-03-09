package com.enlanguage.system.server.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "boxwords")
public class BoxWord implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Column(name = "base_boxword")
  private String baseBoXword;
  @Column(name = "base_boxword_lang")
  private String baseBoxWordLang;
  @NotNull
  @Column(name = "meaning_boxword")
  private String meaningBoxWord;
  @Column(name = "meaning_boxword_lang")
  private String meaningBoxWordLang;
  @Column(name = "created_at")
  private Date createdAt = new Date();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

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
