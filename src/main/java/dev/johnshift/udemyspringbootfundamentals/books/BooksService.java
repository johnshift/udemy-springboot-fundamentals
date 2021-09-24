package dev.johnshift.udemyspringbootfundamentals.books;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BooksService {

  @Autowired
  BooksRepository repository;

  public String buildId(String isbn, int aisle) {
    return isbn + aisle;
  }

  public boolean bookExists(String id) {
    Optional<Book> book = repository.findById(id);

    if (book.isPresent()) {
      return true;
    }

    return false;
  }
}
