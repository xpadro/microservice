package com.xpadro.bookstore.rental;

import com.xpadro.bookstore.entity.BookRental;

import java.util.List;

public interface BookRentalClient {
    List<BookRental> getUserRentals(String userId);
}
