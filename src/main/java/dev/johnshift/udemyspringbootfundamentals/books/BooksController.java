package dev.johnshift.udemyspringbootfundamentals.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

  @Autowired
  BooksRepository repository;

  @Autowired
  AddBookResponse addBookResponse;

  @Autowired
  BooksService booksService;

  @PostMapping("/add-book")
  public ResponseEntity<AddBookResponse> addBook(@RequestBody Books book) {

    // set id for new book
    String id = booksService.buildId(book.getIsbn(), book.getAisle());

    // check if book already exists
    if (!booksService.bookAlreadyExist(id)) {

      // save to db
      book.setId(id);
      repository.save(book);

      // set json response
      addBookResponse.setMessage("Book has been successfully added");
      addBookResponse.setId(id);

      // set headers
      HttpHeaders headers = new HttpHeaders();
      headers.add("unique", id);

      // build http response
      return new ResponseEntity<AddBookResponse>(addBookResponse, headers, HttpStatus.CREATED);
    }

    // handle book already exists
    addBookResponse.setMessage("Book already exists");
    addBookResponse.setId(id);
    return new ResponseEntity<AddBookResponse>(addBookResponse, HttpStatus.ACCEPTED);

  }
}

@Component
class AddBookResponse {
  public String message;
  public String id;

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

}