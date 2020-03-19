package com.xpadro.bookshelf.service;

import com.xpadro.bookshelf.entity.Book;
import com.xpadro.bookshelf.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final String BOOK_TITLE = "a book";

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;
    private List<Book> books = asList(
            new Book("title1", 2019, "author1", "1234567890123"),
            new Book("title2", 2020, "author2", "1234567890124"));

    @Test
    void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();
        assertThat(result.size(), equalTo(2));
    }

    @Test
    void shouldReturnTheRequestedBookIfFound() {
        when(bookRepository.findByIsbn("1234567890123"))
                .thenReturn(Optional.of(new Book(BOOK_TITLE, 2020, "an author", "1234567890123")));

        Book result = bookService.find("1234567890123");

        assertThat(result.getTitle(), equalTo(BOOK_TITLE));
    }

    @Test
    void shouldRaiseExceptionIfNotFound() {
        when(bookRepository.findByIsbn("1234567890123")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.find("1234567890123"));
    }

    @Test
    public void shouldSaveANewBook() {
        Book book = new Book("test title", 1980, "test author", "1234567890125");
        bookService.save(book);

        verify(bookRepository, times(1)).save(book);
    }
}