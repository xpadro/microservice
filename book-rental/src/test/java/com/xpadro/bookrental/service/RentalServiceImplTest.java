package com.xpadro.bookrental.service;

import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        when(repository.save(any(Rental.class))).thenReturn(rental);

        Rental result = service.rentBook(rental);

        assertThat(result.getUserId(), equalTo(rental.getUserId()));
        assertThat(result.getIsbn(), equalTo(rental.getIsbn()));
    }
}
