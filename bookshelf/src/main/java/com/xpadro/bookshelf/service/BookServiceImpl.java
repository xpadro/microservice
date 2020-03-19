package com.xpadro.bookshelf.service;

import com.xpadro.bookshelf.entity.Book;
import com.xpadro.bookshelf.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book find(String isbn) {
        Optional<Book> result = bookRepository.findByIsbn(isbn);

        if (result.isPresent()) {
            return result.get();
        }

        throw new BookNotFoundException(String.format("Book with Isbn %s not found", isbn));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
