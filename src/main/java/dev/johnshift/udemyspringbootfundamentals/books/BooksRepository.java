package dev.johnshift.udemyspringbootfundamentals.books;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Books, String> {

}
