package dev.johnshift.udemyspringbootfundamentals.books;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

  @Column(name = "book_name")
  private String bookName;

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "aisle")
  private int aisle;

  @Column(name = "author")
  private String author;

  public String getBookName() {
    return this.bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
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
