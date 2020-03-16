package com.xpadro.bookrental.integration;

import com.xpadro.bookrental.BookRentalApplication;
import com.xpadro.bookrental.UserAlreadyRentedException;
import com.xpadro.bookrental.entity.Rental;
import com.xpadro.bookrental.repository.RentalRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainerProvider;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(initializers = RentalRepositoryIT.Initializer.class)
@SpringBootTest(classes = BookRentalApplication.class)
//Implemented in JUnit 4, since testcontainers does not yet have support in JUnit 5
@RunWith(SpringJUnit4ClassRunner.class)
public class RentalRepositoryIT {

    @Autowired
    private RentalRepository repository;

    @ClassRule
    public static final JdbcDatabaseContainer<?> mySQLContainer = new MySQLContainerProvider()
            .newInstance()
            .withDatabaseName("database_integration_test")
            .withUsername("testuser")
            .withPassword("testpwd")
            .withInitScript("sql/testcontainers_init.sql");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void shouldRentABook() {
        Rental rental = new Rental("user_3", "1234567890125");
        Rental result = repository.save(rental);

        assertThat(result.getIsbn(), equalTo("1234567890125"));
        assertThat(result.getUserId(), equalTo("user_3"));

        Optional<Rental> savedRental = repository.findById(result.getId());
        assertTrue(savedRental.isPresent());
    }

    @Test(expected = UserAlreadyRentedException.class)
    public void shouldNotRentABookIfUserAlreadyRented() {
        Rental rental = new Rental("user_1", "1234567890126");
        repository.rent(rental);
    }

}
