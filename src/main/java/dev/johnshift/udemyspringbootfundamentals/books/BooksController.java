package dev.johnshift.udemyspringbootfundamentals.books;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

  @Autowired
  BooksRepository repository;

  @Autowired
  AddBookResponse addBookResponse;

  @Autowired
  BookErrorResponse bookErrorResponse;

  @Autowired
  BooksService booksService;

  @PostMapping("/books")
  public ResponseEntity<AddBookResponse> addBook(@RequestBody Book book) {

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

  @GetMapping("/books/{id}")
  public ResponseEntity<Object> getBookById(@PathVariable(value = "id") String id) {

    try {
      Book book = repository.findById(id).get();
      return new ResponseEntity<Object>(book, HttpStatus.OK);
    } catch (Exception e) {
      bookErrorResponse.setInfo("book_id '" + id + "' not found");
      bookErrorResponse.setMessage("Book does not exists");
      return new ResponseEntity<Object>(bookErrorResponse, HttpStatus.NOT_FOUND);
    }

  }

  @GetMapping("/books/author")
  public List<Book> getBookbyAuthor(@RequestParam(value = "name") String name) {
    return repository.findAllByAuthor(name);
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

@Component
class BookErrorResponse {
  public String message;
  public String info;

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getInfo() {
    return this.info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

}