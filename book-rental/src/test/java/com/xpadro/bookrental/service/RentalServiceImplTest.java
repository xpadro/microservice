package com.xpadro.bookrental.service;

import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void shouldFindUserRentals() {
        when(repository.findByUserId("user_8")).thenReturn(singletonList(rental));

        List<Rental> result = service.findRentals("user_8");

        assertThat(result.size(), equalTo(1));
    }

    @Test
    public void shouldNotFindAnUnExistingRental() {
        when(repository.findByUserId("user_8")).thenReturn(emptyList());

        List<Rental> result = service.findRentals("user_8");

        assertTrue(result.isEmpty());
    }
}
