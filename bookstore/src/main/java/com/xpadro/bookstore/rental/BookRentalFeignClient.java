package com.xpadro.bookstore.rental;

import com.xpadro.bookstore.entity.BookRental;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "book-rental-service", url = "http://localhost:8081")
public interface BookRentalFeignClient {

    @GetMapping(value = "/rentals/{userId}")
    public List<BookRental> findUserRentals(@PathVariable String userId);
}
