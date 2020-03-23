package com.xpadro.bookstore;

import com.xpadro.bookstore.details.BookDetailsClient;
import com.xpadro.bookstore.entity.BookDetails;
import com.xpadro.bookstore.entity.BookRental;
import com.xpadro.bookstore.rental.BookDetailsFeignClient;
import com.xpadro.bookstore.rental.BookRentalClient;
import com.xpadro.bookstore.rental.BookRentalFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/store")
public class BookStoreController {
    private final Logger logger = LoggerFactory.getLogger(BookStoreController.class);

    private final BookRentalClient rentalClient;
    private final BookDetailsClient bookClient;

    private final BookRentalFeignClient rentalFeignClient;
    private final BookDetailsFeignClient detailsFeignClient;

    @Autowired
    public BookStoreController(BookRentalClient rentalClient, BookDetailsClient bookClient, BookRentalFeignClient rentalFeignClient, BookDetailsFeignClient detailsFeignClient) {
        this.rentalClient = rentalClient;
        this.bookClient = bookClient;
        this.rentalFeignClient = rentalFeignClient;
        this.detailsFeignClient = detailsFeignClient;
    }

    @GetMapping("/users/{userId}/books")
    public ResponseEntity<List<BookDetails>> getUserBooks(@PathVariable String userId) {
        List<BookRental> userRentals = rentalClient.getUserRentals(userId);

        List<BookDetails> books = userRentals.stream()
                .map(rental -> bookClient.getBook(rental.getIsbn()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return ResponseEntity.ok(books);
    }

    @GetMapping("/feign/users/{userId}/books")
    public ResponseEntity<List<BookDetails>> getUserBooksWithFeign(@PathVariable String userId) {
        List<BookRental> userRentals = rentalFeignClient.findUserRentals(userId);

        List<BookDetails> books = userRentals.stream()
                .map(rental -> getBookDetails(rental.getIsbn()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return ResponseEntity.ok(books);
    }

    private Optional<BookDetails> getBookDetails(String isbn) {
        BookDetails bookDetails = null;

        try {
            bookDetails = detailsFeignClient.getBook(isbn);
        } catch (FeignException e) {
            logger.warn("Could not retrieve book. Cause: {}", e.getMessage());
        }

        return Optional.ofNullable(bookDetails);
    }
}
