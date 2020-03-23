package com.xpadro.bookstore;

import com.xpadro.bookstore.bookdetails.BookDetails;
import com.xpadro.bookstore.bookdetails.rest.BookDetailsRestClient;
import com.xpadro.bookstore.bookrental.BookRental;
import com.xpadro.bookstore.bookrental.rest.BookRentalRestClient;
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
public class BookStoreRestController {
    private final BookRentalRestClient rentalClient;
    private final BookDetailsRestClient bookClient;

    @Autowired
    public BookStoreRestController(BookRentalRestClient rentalClient, BookDetailsRestClient bookClient) {
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
