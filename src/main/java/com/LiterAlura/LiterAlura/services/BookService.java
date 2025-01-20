package com.LiterAlura.LiterAlura.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.LiterAlura.LiterAlura.clients.GutendexClient;
import com.LiterAlura.LiterAlura.dto.BookDTO;
import com.LiterAlura.LiterAlura.models.Author;
import com.LiterAlura.LiterAlura.models.Book;
import com.LiterAlura.LiterAlura.repositories.AuthorRepository;
import com.LiterAlura.LiterAlura.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GutendexClient client;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GutendexClient client) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.client = client;
    }

    // -----------------------------
    // Funciones relacionadas con libros
    // -----------------------------

    public long countBooksByLanguage(String language) {
        List<Book> books = bookRepository.findByLanguage(language);
        return books.size();
    }

    public void showBookStatisticsByLanguage(String language) {
        long count = countBooksByLanguage(language);
        System.out.println("Cantidad de libros en el idioma \"" + language + "\": " + count);
    }

    // -----------------------------
    // Funciones relacionadas con la API Gutendex
    // -----------------------------

    public void fetchAndSaveBookByTitle(String title) throws Exception {
        try {
            // Obtener los datos desde la API
            JsonNode bookData = client.searchBooksByTitle(title).get("results");
            if (bookData.isEmpty()) {
                System.out.println("No se encontraron libros con el título: " + title);
                return;
            }

            JsonNode bookInfo = bookData.get(0); // Tomamos el primer resultado
            BookDTO bookDTO = new ObjectMapper().treeToValue(bookInfo, BookDTO.class);

            // Verificación de si los autores están disponibles
            if (bookDTO.getAuthors() == null || bookDTO.getAuthors().isEmpty()) {
                System.out.println("No se encontraron autores para el libro: " + title);
                return;
            }

            // Guardar los datos del autor
            Author author = new Author();
            author.setName(bookDTO.getAuthors().get(0).getName());
            author.setBirthYear(bookDTO.getAuthors().get(0).getBirth_year());
            author.setDeathYear(bookDTO.getAuthors().get(0).getDeathYear());
            authorRepository.save(author);

            // Guardar los datos del libro
            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setLanguage(bookDTO.getLanguages().get(0));
            book.setDownloadCount(bookDTO.getDownloadCount());
            book.setAuthor(author);
            bookRepository.save(book);

            // Imprimir los detalles del libro y autor en el formato requerido
            System.out.println("-------LIBRO-------");
            System.out.println("Título: \"" + bookDTO.getTitle() + "\"");
            System.out.println("Autor: \"" + author.getName() + "\"");
            System.out.println("Idioma: \"" + bookDTO.getLanguages().get(0) + "\"");
            System.out.println("Descargas: \"" + bookDTO.getDownloadCount() + "\"");
            System.out.println("--------------------------------------------");

        } catch (Exception e) {
            // Manejo de excepciones
            System.out.println("Ocurrió un error al procesar la solicitud para el libro: " + title);
            e.printStackTrace();
        }
    }
    public void listAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }
        for (Book book : books) {
            System.out.println("-------LIBRO-------");
            System.out.println("Título: \"" + book.getTitle() + "\"");
            System.out.println("Autor: \"" + book.getAuthor().getName() + "\"");
            System.out.println("Idioma: \"" + book.getLanguage() + "\"");
            System.out.println("Descargas: \"" + book.getDownloadCount() + "\"");
            System.out.println("--------------------------------------------");
        }
    }

    public void showBookStatisticsByLanguage() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }

        // Crear un mapa para contar libros por idioma
        Map<String, Long> languageStats = new HashMap<>();

        for (Book book : books) {
            String language = book.getLanguage();
            languageStats.put(language, languageStats.getOrDefault(language, 0L) + 1);
        }

        // Mostrar las estadísticas
        for (Map.Entry<String, Long> entry : languageStats.entrySet()) {
            System.out.println("Idioma: " + entry.getKey() + " - Cantidad de libros: " + entry.getValue());
        }
    }
    // Método en BookService para buscar libros por idioma
    public void searchBooksByLanguage(String language) {
        List<Book> books = bookRepository.findByLanguage(language);

        if (books.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + language);
            return;
        }

        // Imprimir los libros encontrados
        for (Book book : books) {
            System.out.println("-------LIBRO-------");
            System.out.println("Título: \"" + book.getTitle() + "\"");
            System.out.println("Autor: \"" + book.getAuthor().getName() + "\"");
            System.out.println("Idioma: \"" + book.getLanguage() + "\"");
            System.out.println("Descargas: \"" + book.getDownloadCount() + "\"");
            System.out.println("--------------------------------------------");
        }
    }

}
