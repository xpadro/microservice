package com.xpadro.bookstore;

import com.xpadro.bookstore.bookdetails.BookDetails;
import com.xpadro.bookstore.bookdetails.feign.BookDetailsFeignClient;
import com.xpadro.bookstore.bookrental.BookRental;
import com.xpadro.bookstore.bookrental.feign.BookRentalFeignClient;
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
@RequestMapping(value = "/store/feign")
public class BookStoreFeignController {
    private final Logger logger = LoggerFactory.getLogger(BookStoreFeignController.class);

    private final BookRentalFeignClient rentalFeignClient;
    private final BookDetailsFeignClient detailsFeignClient;

    @Autowired
    public BookStoreFeignController(BookRentalFeignClient rentalFeignClient, BookDetailsFeignClient detailsFeignClient) {
        this.rentalFeignClient = rentalFeignClient;
        this.detailsFeignClient = detailsFeignClient;
    }

    @GetMapping("/users/{userId}/books")
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
