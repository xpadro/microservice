package com.xpadro.bookrental;

import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
                .buildAndExpand(savedRental.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public @ResponseBody String test() {
        return "Hello";
    }
}
