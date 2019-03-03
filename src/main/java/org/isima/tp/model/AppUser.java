package org.isima.tp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class AppUser {

  @Id
  String email;

  @Column
  String name;

  @Column
  @JsonIgnore
  byte[] password;

  @Column
  @JsonIgnore
  String resetPasswordLink;

  @ManyToMany(fetch = FetchType.EAGER)
  List<Community> communities;

  @Transient
  String token;

  public AppUser() {
  }

  public AppUser(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  @SuppressWarnings("UnusedReturnValue")
  public AppUser setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getName() {
    return name;
  }

  public AppUser setName(String name) {
    this.name = name;
    return this;
  }

  public byte[] getPassword() {
    return password;
  }

  @SuppressWarnings("UnusedReturnValue")
  public AppUser setPassword(byte[] password) {
    this.password = password;
    return this;
  }

  public String getResetPasswordLink() {
    return resetPasswordLink;
  }

  public AppUser setResetPasswordLink(String resetPasswordLink) {
    this.resetPasswordLink = resetPasswordLink;
    return this;
  }

  public List<Community> getCommunities() {
    return communities;
  }

  public AppUser setCommunities(List<Community> communities) {
    this.communities = communities;
    return this;
  }

  public String getToken() {
    return token;
  }

  @SuppressWarnings("UnusedReturnValue")
  public AppUser setToken(String token) {
    this.token = token;
    return this;
  }
}
