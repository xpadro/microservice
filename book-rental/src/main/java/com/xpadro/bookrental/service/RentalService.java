package com.xpadro.bookrental.service;

import com.xpadro.bookrental.entity.Rental;

public interface RentalService {

    Rental rentBook(String userId, String isbn);
}
