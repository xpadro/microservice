package com.xpadro.bookrental.service;

import com.xpadro.bookrental.RentalNotFoundException;
import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceImplTest {

    @Mock
    private RentalRepository repository;

    @InjectMocks
    private RentalServiceImpl service;

    private Rental rental = new Rental("user_8", "1234567890127");

    @Test
    public void shouldRentABook() {
        when(repository.rent(any(Rental.class))).thenReturn(rental);

        Rental result = service.rentBook(rental);

        assertThat(result.getUserId(), equalTo(rental.getUserId()));
        assertThat(result.getIsbn(), equalTo(rental.getIsbn()));
    }

    @Test
    public void shouldFindTheRequestedRental() {
        when(repository.findByUserId("user_8")).thenReturn(Optional.of(rental));

        Rental result = service.findRental("user_8");

        assertThat(result.getUserId(), equalTo("user_8"));
    }

    @Test
    public void shouldNotFindAnUnExistingRental() {
        when(repository.findByUserId("user_8")).thenReturn(Optional.empty());

        Assertions.assertThrows(RentalNotFoundException.class, () -> service.findRental("user_8"));
    }
}
