package dev.johnshift.udemyspringbootfundamentals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;

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

  @MockBean
  BooksRepository booksRepository;

  @MockBean
  BooksService booksService;

  @Autowired
  BooksController booksController;

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

  @Test
  public void getBookbyAuthorTest() throws Exception {

    // mock
    List<Book> books = new ArrayList<Book>();
    books.add(createBook());
    books.add(createBook());
    when(booksRepository.findAllByAuthor(any())).thenReturn(books);

    // mockmvc
    this.mockMvc.perform(get("/books/author").param("name", "johnshift")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(2))).andExpect(jsonPath("$.[0].id").value("NEW_ISBN999"));
  }

  @Test
  public void updateBookTest() throws Exception {

    // mock
    Book book = createBook();
    ObjectMapper map = new ObjectMapper();
    Book updatedBook = updateBook(book);
    String jsonPayload = map.writeValueAsString(updatedBook);
    when(booksService.getBookById(any())).thenReturn(book);

    // mockmvc
    this.mockMvc.perform(put("/books").contentType(MediaType.APPLICATION_JSON).content(jsonPayload)).andDo(print())
        .andExpect(status().isOk()).andExpect(content().json(
            "{\"bookName\":\"Updated Book Name\",\"id\":\"NEW_ISBN999\",\"isbn\":\"NEW_ISBN\",\"aisle\":999,\"author\":\"johnshift2\"}"));
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

  public Book updateBook(Book prev) {
    Book book = new Book();

    // updated fields
    book.setBookName("Updated Book Name");
    book.setAuthor("johnshift2");

    // // retained fields
    // book.setIsbn(prev.getIsbn());
    // book.setId(prev.getId());
    // book.setAisle(prev.getAisle());

    return book;
  }

}
