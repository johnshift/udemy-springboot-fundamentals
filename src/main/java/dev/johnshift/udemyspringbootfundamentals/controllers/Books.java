package dev.johnshift.udemyspringbootfundamentals.controllers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books") // map class to table_name
public class Books {

  @Column(name = "book_name")
  private String book_name;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "aisle")
  private int aisle;

  @Column(name = "author")
  private String author;

  public String getBook_name() {
    return this.book_name;
  }

  public void setBook_name(String book_name) {
    this.book_name = book_name;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getAisle() {
    return this.aisle;
  }

  public void setAisle(int aisle) {
    this.aisle = aisle;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

}
