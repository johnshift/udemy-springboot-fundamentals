package dev.johnshift.udemyspringbootfundamentals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.johnshift.udemyspringbootfundamentals.books.Book;
import dev.johnshift.udemyspringbootfundamentals.books.BooksController;
import dev.johnshift.udemyspringbootfundamentals.books.BooksRepository;
import dev.johnshift.udemyspringbootfundamentals.books.BooksService;
import dev.johnshift.udemyspringbootfundamentals.books.ResponseAddBook;

@SpringBootTest
class UdemySpringbootFundamentalsApplicationTests {

  @Autowired
  BooksController booksController;

  @MockBean
  BooksRepository booksRepository;

  @MockBean
  BooksService booksService;

  @Test
  void contextLoads() {
  }

  @Test
  public void addBookTest() {

    // mock
    Book book = createBook();
    // mock booksService.buildId
    when(booksService.buildId(book.getIsbn(), book.getAisle())).thenReturn(book.getId());
    // mock bookService.bookExists
    when(booksService.bookExists(book.getId())).thenReturn(false);

    // test http response
    ResponseEntity<ResponseAddBook> response = booksController.addBook(createBook());
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    // test response body
    ResponseAddBook responseBody = response.getBody();
    assertEquals(book.getId(), responseBody.id);
    assertEquals("Book has been successfully added", responseBody.message);

  }

  public Book createBook() {
    Book book = new Book();

    book.setAisle(999);
    book.setBookName("Some Book Name");
    book.setIsbn("NEW_ISBN");
    book.setAuthor("johnshift");
    book.setId("NEW_ISBN999");

    return book;
  }

}
