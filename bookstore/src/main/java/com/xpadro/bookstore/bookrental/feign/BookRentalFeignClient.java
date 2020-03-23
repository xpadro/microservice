package com.xpadro.bookstore.bookrental.feign;

import com.xpadro.bookstore.bookrental.BookRental;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "book-rental-service")
public interface BookRentalFeignClient {

    @GetMapping(value = "/rentals/{userId}")
    List<BookRental> findUserRentals(@PathVariable String userId);
}
