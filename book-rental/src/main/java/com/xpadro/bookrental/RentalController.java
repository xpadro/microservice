package com.xpadro.bookrental;

import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping()
    public ResponseEntity<Rental> rentBook(@Valid @RequestBody Rental rental) {
        Rental savedRental = rentalService.rentBook(rental);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRental.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<Rental>> findUserRentals(@PathVariable String userId) {
        List<Rental> userRentals = rentalService.findRentals(userId);

        return ResponseEntity.ok(userRentals);
    }
}
