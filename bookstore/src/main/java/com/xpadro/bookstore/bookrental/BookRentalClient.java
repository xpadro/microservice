package com.xpadro.bookstore.bookrental;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "book-rental-service")
public interface BookRentalClient {

    @GetMapping(value = "/rentals/{userId}")
    List<BookRental> findUserRentals(@PathVariable String userId);
}
