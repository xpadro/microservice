package com.xpadro.bookrental.service;

import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental rentBook(Rental rental) {
        Rental newRental = new Rental(rental.getUserId(), rental.getIsbn());
        return rentalRepository.rent(newRental);
    }

    @Override
    public List<Rental> findRentals(String userId) {
        return rentalRepository.findByUserId(userId);
    }
}
