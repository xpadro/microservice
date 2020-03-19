package com.xpadro.bookstore.details;

import com.xpadro.bookstore.entity.BookDetails;

import java.util.Optional;

public interface BookDetailsClient {
    Optional<BookDetails> getBook(String isbn);
}
