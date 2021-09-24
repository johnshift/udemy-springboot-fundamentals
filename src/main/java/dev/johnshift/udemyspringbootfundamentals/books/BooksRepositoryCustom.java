package dev.johnshift.udemyspringbootfundamentals.books;

import java.util.List;

public interface BooksRepositoryCustom {

  List<Book> findAllByAuthor(String name);
}