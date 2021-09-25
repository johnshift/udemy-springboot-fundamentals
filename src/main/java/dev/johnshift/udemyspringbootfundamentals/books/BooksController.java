package dev.johnshift.udemyspringbootfundamentals.books;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

  @Autowired
  BooksRepository repository;

  @Autowired
  ResponseAddBook responseAddBook;

  @Autowired
  BookErrorResponse bookErrorResponse;

  @Autowired
  BooksService booksService;

  private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

  @PostMapping("/books")
  public ResponseEntity<ResponseAddBook> addBook(@RequestBody Book book) {

    // set id for new book
    String id = booksService.buildId(book.getIsbn(), book.getAisle());

    // check if book already exists
    if (!booksService.bookExists(id)) {

      // log
      logger.info("Creating new book", book);

      // save to db
      book.setId(id);
      repository.save(book);

      // set json response
      responseAddBook.setMessage("Book has been successfully added");
      responseAddBook.setId(id);

      // set headers
      HttpHeaders headers = new HttpHeaders();
      headers.add("unique", id);

      // build http response
      return new ResponseEntity<ResponseAddBook>(responseAddBook, headers, HttpStatus.CREATED);
    }

    // log
    logger.info("Book does not exist.", book);

    // handle book already exists
    responseAddBook.setMessage("Book already exists");
    responseAddBook.setId(id);
    return new ResponseEntity<ResponseAddBook>(responseAddBook, HttpStatus.ACCEPTED);

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

  @PutMapping("/books")
  public ResponseEntity<Book> updateBook(@RequestBody Book book) {

    // Book retrievedBook = repository.findById(book.getId()).get();
    Book retrievedBook = booksService.getBookById(book.getId());

    retrievedBook.setAuthor(book.getAuthor());
    retrievedBook.setBookName(book.getBookName());

    repository.save(retrievedBook);

    return new ResponseEntity<Book>(retrievedBook, HttpStatus.OK);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") String id) {

    if (!booksService.bookExists(id)) {

      bookErrorResponse.setInfo("book_id '" + id + "' not found");
      bookErrorResponse.setMessage("Book does not exists");
      return new ResponseEntity<Object>(bookErrorResponse, HttpStatus.BAD_REQUEST);
    }

    repository.deleteById(id);
    return new ResponseEntity<Object>(HttpStatus.OK);

  }

  @GetMapping("/books")
  public List<Book> getAllBooks() {

    return repository.findAll();
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