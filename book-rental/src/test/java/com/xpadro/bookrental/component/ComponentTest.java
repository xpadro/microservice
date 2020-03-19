package com.xpadro.bookrental.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpadro.bookrental.BookRentalApplication;
import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookRentalApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("dev")
public class ComponentTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RentalRepository rentalRepository;

    @Test
    public void shouldRentABook() throws Exception {
        Rental newRental = new Rental("user_3", "1234567890124");

        MvcResult mvcResult = mockMvc.perform(post("/rentals")
                .content(objectMapper.writeValueAsString(newRental))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("location"), equalTo("http://localhost/rentals/user_3"));

        List<Rental> userRentals = rentalRepository.findByUserId("user_3");

        assertThat(userRentals.size(), equalTo(1));
        assertThat(userRentals.get(0).getUserId(), equalTo(newRental.getUserId()));
    }

    @Test
    public void userShouldBeAbleToRentMultipleBooks() throws Exception {
        Rental newRental = new Rental("user_1", "1234567890125");

        MvcResult mvcResult = mockMvc.perform(post("/rentals")
                .content(objectMapper.writeValueAsString(newRental))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("location"), equalTo("http://localhost/rentals/user_1"));

        List<Rental> userRentals = rentalRepository.findByUserId("user_1");

        assertThat(userRentals.size(), equalTo(2));
        assertTrue(userRentals.stream().anyMatch(rental -> rental.getIsbn().equals("1234567890125")));
    }

    @Test
    public void userShouldNotRentAnAlreadyRentedBook() throws Exception {
        Rental newRental = new Rental("user_1", "1234567890122");

        mockMvc.perform(post("/rentals")
                .content(objectMapper.writeValueAsString(newRental))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindAnExistingRental() throws Exception {
        MvcResult result = mockMvc.perform(get("/rentals/user_1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Rental[] userRentals = objectMapper.readValue(contentAsString, Rental[].class);

        assertThat(userRentals[0].getUserId(), equalTo("user_1"));
    }

    @Test
    public void shouldNotFindAnUnExistingRental() throws Exception {
        MvcResult result = mockMvc.perform(get("/rentals/user_9")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Rental[] userRentals = objectMapper.readValue(contentAsString, Rental[].class);

        assertThat(userRentals.length, equalTo(0));
    }
}
