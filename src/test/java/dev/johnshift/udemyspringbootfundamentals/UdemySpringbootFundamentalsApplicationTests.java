package dev.johnshift.udemyspringbootfundamentals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;

import dev.johnshift.udemyspringbootfundamentals.books.Book;
import dev.johnshift.udemyspringbootfundamentals.books.BooksController;
import dev.johnshift.udemyspringbootfundamentals.books.BooksRepository;
import dev.johnshift.udemyspringbootfundamentals.books.BooksService;
import dev.johnshift.udemyspringbootfundamentals.books.ResponseAddBook;

@SpringBootTest
@AutoConfigureMockMvc
class UdemySpringbootFundamentalsApplicationTests {

  @Autowired
  BooksController booksController;

  @MockBean
  BooksRepository booksRepository;

  @MockBean
  BooksService booksService;

  @Autowired
  MockMvc mockMvc;

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
    // mock booksRepository.save
    when(booksRepository.save(any())).thenReturn(book);

    // test http response
    ResponseEntity<ResponseAddBook> response = booksController.addBook(createBook());
    assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    // test response body
    ResponseAddBook responseBody = response.getBody();
    assertEquals(book.getId(), responseBody.id);
    assertEquals("Book has been successfully added", responseBody.message);

  }

  @Test
  public void addBookControllerTest() throws Exception {

    // mockito
    Book book = createBook();
    ObjectMapper map = new ObjectMapper();
    String jsonPayload = map.writeValueAsString(book);
    when(booksService.buildId(book.getIsbn(), book.getAisle())).thenReturn(book.getId());
    when(booksService.bookExists(book.getId())).thenReturn(false);
    when(booksRepository.save(any())).thenReturn(book);

    // mockmvc
    this.mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
        .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(book.getId()))
        .andExpect(jsonPath("$.message").value("Book has been successfully added"));

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
