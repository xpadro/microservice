package com.xpadro.bookstore.bookdetails.rest;

import com.xpadro.bookstore.bookdetails.BookDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BookDetailsRestClientImpl implements BookDetailsRestClient {
    private static final Logger logger = LoggerFactory.getLogger(BookDetailsRestClientImpl.class);

    @Value("${book.details.url}")
    private String serviceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public BookDetailsRestClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<BookDetails> getBook(String isbn) {
        BookDetails bookDetails;

        try {
            ResponseEntity<BookDetails> response = restTemplate.getForEntity(serviceUrl, BookDetails.class, isbn);
            bookDetails = response.getBody();
        } catch (Exception e) {
            logger.warn(String.format("Book with isbn %s not retrieved. Error: %s", isbn, e.getMessage()));
            bookDetails = null;
        }

        return Optional.ofNullable(bookDetails);
    }
}