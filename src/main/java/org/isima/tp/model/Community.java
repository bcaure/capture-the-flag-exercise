package org.isima.tp.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Community {

  @Id
  private String name;

  @Transient
  private List<AppUser> users;

  public String getName() {
    return name;
  }

  public Community setName(String name) {
    this.name = name;
    return this;
  }

  public List<AppUser> getUsers() {
    return users;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Community setUsers(List<AppUser> users) {
    this.users = users;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Community community = (Community) o;
    return Objects.equals(name, community.name);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name);
  }
}
