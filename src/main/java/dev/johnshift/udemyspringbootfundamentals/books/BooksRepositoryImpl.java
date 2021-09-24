package dev.johnshift.udemyspringbootfundamentals.books;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BooksRepositoryImpl implements BooksRepositoryCustom {

  @Autowired
  BooksRepository repository;

  @Override
  public List<Book> findAllByAuthor(String name) {

    List<Book> results = new ArrayList<Book>();
    List<Book> books = repository.findAll();

    for (Book book : books) {
      if (book.getAuthor().equalsIgnoreCase(name)) {
        results.add(book);
      }
    }

    return results;
  }
}
