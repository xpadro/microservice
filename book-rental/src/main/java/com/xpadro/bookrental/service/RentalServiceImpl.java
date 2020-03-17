package com.xpadro.bookrental.service;

import com.xpadro.bookrental.RentalNotFoundException;
import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Rental findRental(String userId) {
        return rentalRepository.findByUserId(userId).orElseThrow(RentalNotFoundException::new);
    }
}
