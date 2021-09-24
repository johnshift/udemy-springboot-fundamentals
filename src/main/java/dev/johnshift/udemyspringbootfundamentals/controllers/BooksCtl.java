package dev.johnshift.udemyspringbootfundamentals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.johnshift.udemyspringbootfundamentals.repository.BooksRepository;

@RestController
public class BooksCtl {

  @Autowired
  BooksRepository repository;

  @Autowired
  AddBookResponse addResponse;

  @PostMapping("/add-book")
  public ResponseEntity<AddBookResponse> addBook(@RequestBody Books book) {

    // set id for new book
    book.setId(book.getIsbn() + book.getAisle());
    repository.save(book);

    // set json response
    addResponse.setMessage("Book has been successfully added");
    addResponse.setId(book.getId());

    // set headers
    HttpHeaders headers = new HttpHeaders();
    headers.add("unique", book.getId());

    // build http response
    return new ResponseEntity<AddBookResponse>(addResponse, headers, HttpStatus.CREATED);
  }
}
