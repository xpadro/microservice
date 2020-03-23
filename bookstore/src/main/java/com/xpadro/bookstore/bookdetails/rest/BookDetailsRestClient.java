package com.xpadro.bookstore.bookdetails.rest;

import com.xpadro.bookstore.bookdetails.BookDetails;

import java.util.Optional;

public interface BookDetailsRestClient {
    Optional<BookDetails> getBook(String isbn);
}
