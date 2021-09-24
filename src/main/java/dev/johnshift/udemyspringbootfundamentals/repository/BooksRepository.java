package dev.johnshift.udemyspringbootfundamentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.johnshift.udemyspringbootfundamentals.controllers.Books;

public interface BooksRepository extends JpaRepository<Books, String> {

}
