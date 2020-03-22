package com.xpadro.bookstore.rental;

import com.xpadro.bookstore.entity.BookRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Service
public class BookRentalClientImpl implements BookRentalClient {
    @Value("${book.rental.url}")
    private String serviceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public BookRentalClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<BookRental> getUserRentals(String userId) {
        ResponseEntity<BookRental[]> response = restTemplate.getForEntity(serviceUrl, BookRental[].class, userId);
        BookRental[] userRentals = response.getBody();

        return userRentals != null ? asList(userRentals) : emptyList();
    }
}
