package com.enlanguage.system.server.model;

import com.enlanguage.system.server.model.enumeration.RoleType;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "role")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  public Role() {
  }

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "role", nullable = false, unique = true)
  private RoleType type;

  @CreatedDate
  @Type(type = "java.sql.Timestamp")
  @Column(name = "created_at", updatable = false)
  private Date createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public RoleType getType() {
    return type;
  }

  public void setType(RoleType type) {
    this.type = type;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
