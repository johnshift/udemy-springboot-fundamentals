package dev.johnshift.udemyspringbootfundamentals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.johnshift.udemyspringbootfundamentals.repository.BooksRepository;

@RestController
public class BooksCtl {

  @Autowired
  BooksRepository repository;

  @PostMapping("/add-book")
  public void addBook(@RequestBody Books book) {

    // set id for new book
    book.setId(book.getIsbn() + book.getAisle());
    repository.save(book);
  }
}
