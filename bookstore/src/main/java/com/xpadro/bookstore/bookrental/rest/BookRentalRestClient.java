package com.xpadro.bookstore.bookrental.rest;

import com.xpadro.bookstore.bookrental.BookRental;

import java.util.List;

public interface BookRentalRestClient {
    List<BookRental> getUserRentals(String userId);
}
