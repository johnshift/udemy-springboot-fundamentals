package dev.johnshift.udemyspringbootfundamentals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.johnshift.udemyspringbootfundamentals.repository.BooksRepository;
import dev.johnshift.udemyspringbootfundamentals.service.BooksService;

@RestController
public class BooksCtl {

  @Autowired
  BooksRepository repository;

  @Autowired
  AddBookResponse addResponse;

  @Autowired
  BooksService booksService;

  @PostMapping("/add-book")
  public ResponseEntity<AddBookResponse> addBook(@RequestBody Books book) {

    // set id for new
    String id = booksService.buildId(book.getIsbn(), book.getAisle());

    // check if book already exists
    if (!booksService.bookAlreadyExist(id)) {
      // save to db
      book.setId(id);
      repository.save(book);

      // set json response
      addResponse.setMessage("Book has been successfully added");
      addResponse.setId(id);

      // set headers
      HttpHeaders headers = new HttpHeaders();
      headers.add("unique", id);

      // build http response
      return new ResponseEntity<AddBookResponse>(addResponse, headers, HttpStatus.CREATED);
    }

    // build response already exists;
    addResponse.setMessage("Book already exists");
    addResponse.setId(id);
    return new ResponseEntity<AddBookResponse>(addResponse, HttpStatus.ACCEPTED);

  }
}
