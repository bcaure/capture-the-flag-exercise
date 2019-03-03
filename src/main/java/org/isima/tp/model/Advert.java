package org.isima.tp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Advert {

  @Id
  String title;

  @Column(length = 1000)
  String content;

  @ManyToOne
  AppUser author;

  @ManyToOne
  Community sponsor;

  public String getTitle() {
    return title;
  }

  public Advert setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getContent() {
    return content;
  }

  public Advert setContent(String content) {
    this.content = content;
    return this;
  }

  public AppUser getAuthor() {
    return author;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Advert setAuthor(AppUser author) {
    this.author = author;
    return this;
  }

  public Community getSponsor() {
    return sponsor;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Advert setSponsor(Community sponsor) {
    this.sponsor = sponsor;
    return this;
  }
}
