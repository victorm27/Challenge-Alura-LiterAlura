package com.LiterAlura.LiterAlura;

import com.LiterAlura.LiterAlura.services.BookService;
import com.LiterAlura.LiterAlura.services.AuthorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	private final BookService bookService;
	private final AuthorService authorService;
	public LiterAluraApplication(BookService bookService, AuthorService authorService) {
		this.bookService = bookService;
        this.authorService = authorService;
    }



	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros");
            System.out.println("3. Listar autores");
            System.out.println("4. Listar autores vivos en un año");
			System.out.println("5. Estadísticas de libros por idioma");
			System.out.println("6. Listar libros por idioma");
            System.out.println("7. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la línea restante

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String title = scanner.nextLine();
                    bookService.fetchAndSaveBookByTitle(title);
                    break;
                case 2:
                    bookService.listAllBooks();
                    break;
                case 3:
                    authorService.listAllAuthors();
                    break;
                case 4:
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    authorService.listAuthorsAliveInYear(year);
                    break;
                case 5:
                    bookService.showBookStatisticsByLanguage();
                    break;
                case 6:
                    // Opción 6: Buscar libros por idioma
                    System.out.println("Seleccione un idioma:");
                    System.out.println("en = Inglés");
                    System.out.println("es = Español");
                    System.out.println("fr = Francés");
                    System.out.println("pt = Portugués");

                    String language = scanner.nextLine().toLowerCase();

                    // Validar que el idioma esté entre los seleccionados
                    if (!language.equals("en") && !language.equals("es") && !language.equals("fr") && !language.equals("pt")) {
                        System.out.println("Idioma no válido. Por favor, elija entre: en (Inglés), es (Español), fr (Francés), pt (Portugués).");
                        break;
                    }

                    // Buscar libros por idioma
                    bookService.searchBooksByLanguage(language);
                    break;
                case 7:
                    // Salir y finalizar el programa
                    System.out.println("Saliendo del programa...");
                    System.exit(0);  // Esto termina la ejecución del programa
                    break;
                default:
                    System.out.println("Opción no válida, por favor seleccione una opción del 1 al 7.");
                    break;
            }
		}
	}
}


