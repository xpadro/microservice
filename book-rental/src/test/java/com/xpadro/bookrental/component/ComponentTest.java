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

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
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
        Rental newRental = new Rental("user_3", "2234567890123");

        MvcResult mvcResult = mockMvc.perform(post("/rentals")
                .content(objectMapper.writeValueAsString(newRental))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("location"), equalTo("http://localhost/rentals/3"));

        Optional<Rental> savedRental = rentalRepository.findById(3L);

        if (!savedRental.isPresent()) {
            fail("rental should be saved");
        } else {
            assertThat(savedRental.get().getUserId(), equalTo(newRental.getUserId()));
        }
    }

    @Test
    public void shouldNotRentABookIfUserAlreadyRented() throws Exception {
        Rental newRental = new Rental("user_1", "3234567890123");

        mockMvc.perform(post("/rentals")
                .content(objectMapper.writeValueAsString(newRental))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
