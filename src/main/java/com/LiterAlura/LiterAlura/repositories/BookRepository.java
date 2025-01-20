package com.LiterAlura.LiterAlura.repositories;

import com.LiterAlura.LiterAlura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguage(String language);
}
