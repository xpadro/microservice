package com.xpadro.bookstore;

import com.xpadro.bookstore.details.BookDetailsClient;
import com.xpadro.bookstore.entity.BookDetails;
import com.xpadro.bookstore.entity.BookRental;
import com.xpadro.bookstore.rental.BookRentalClient;
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

    private final BookRentalClient rentalClient;
    private final BookDetailsClient bookClient;

    @Autowired
    public BookStoreController(BookRentalClient rentalClient, BookDetailsClient bookClient) {
        this.rentalClient = rentalClient;
        this.bookClient = bookClient;
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
}
