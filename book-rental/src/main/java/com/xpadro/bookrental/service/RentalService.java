package com.xpadro.bookrental.service;

import com.xpadro.bookrental.entity.Rental;

import java.util.List;

public interface RentalService {

    Rental rentBook(Rental rental);

    List<Rental> findRentals(String userId);
}
