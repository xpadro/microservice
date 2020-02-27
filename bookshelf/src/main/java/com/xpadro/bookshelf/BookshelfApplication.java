package com.xpadro.bookshelf;

import com.xpadro.bookshelf.entity.Book;
import com.xpadro.bookshelf.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookshelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookshelfApplication.class, args);
	}

	@Bean
	CommandLineRunner fillData(BookRepository bookRepository) {
		return args -> {
			bookRepository.save(new Book("The Lord of the Rings", 1954, "J. R. R. Tolkien"));
			bookRepository.save(new Book("The Little Prince", 1943, "Antoine de Saint-Exupery"));
			bookRepository.save(new Book("Harry Potter and the Philosopher's Stone", 1997, "J. J. Rowling"));
			bookRepository.save(new Book("Alice's Adventures in Wonderland", 1865, "Lewis Carroll"));
		};
	}

}
