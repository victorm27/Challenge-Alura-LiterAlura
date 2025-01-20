package com.LiterAlura.LiterAlura.services;

import com.LiterAlura.LiterAlura.models.Author;
import com.LiterAlura.LiterAlura.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthorsAliveInYear(int year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }

    public void listAuthorsAliveInYear(int year) {
        List<Author> authors = authorRepository.findAll();
        for (Author author : authors) {
            if (author.getBirthYear() <= year && (author.getDeathYear() == null || author.getDeathYear() >= year)) {
                System.out.println("Nombre: \"" + author.getName() + "\"");
                System.out.println("Año de nacimiento: \"" + author.getBirthYear() + "\"");
                System.out.println("Año de muerte: \"" + author.getDeathYear() + "\"");
                System.out.println("--------------------------------------------");
            }
        }
    }
    public void listAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("No hay autores disponibles.");
            return;
        }
        for (Author author : authors) {
            System.out.println("Nombre: \"" + author.getName() + "\"");
            System.out.println("Año de nacimiento: \"" + author.getBirthYear() + "\"");
            System.out.println("Año de muerte: \"" + author.getDeathYear() + "\"");
            System.out.println("--------------------------------------------");
        }
    }
}

