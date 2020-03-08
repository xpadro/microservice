package com.xpadro.bookshelf.integration;

import com.xpadro.bookshelf.BookshelfApplication;
import com.xpadro.bookshelf.entity.Book;
import com.xpadro.bookshelf.repository.BookRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainerProvider;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(initializers = BookRepositoryIT.Initializer.class)
@SpringBootTest(classes = BookshelfApplication.class)
//Implemented in JUnit 4, since testcontainers does not yet have support in JUnit 5
@RunWith(SpringJUnit4ClassRunner.class)
public class BookRepositoryIT {

    @Autowired
    private BookRepository repository;

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
    public void shouldFindBooks() {
        List<Book> result = repository.findAll();

        assertThat(result.size(), equalTo(3));
    }

    @Test
    public void shouldCreateANewBook() {
        Book book = new Book("test title", 2010, "test author", "1234567890126");
        repository.save(book);

        Book result = repository.findByTitle("test title");

        assertThat(result.getTitle(), equalTo(book.getTitle()));
    }

    @Test
    public void shouldNotAllowDuplicatedIsbn() {
        repository.save(new Book("one test title", 2010, "test author", "1234567890127"));

        Book book = new Book("another test title", 2010, "test author", "1234567890127");

        assertThrows(DataIntegrityViolationException.class, () -> repository.save(book));
    }

    @Test
    public void shouldNotAllowShortTitles() {
        Book book = new Book("a", 2010, "test author", "1234567890123");

        assertThrows(ConstraintViolationException.class, () -> repository.save(book));
    }

    @Test
    public void shouldNotAllowShortAuthors() {
        Book book = new Book("test title", 2010, "a", "1234567890123");

        assertThrows(ConstraintViolationException.class, () -> repository.save(book));
    }

    @Test
    public void shouldNotAllowShortIsbn() {
        Book book = new Book("test title", 2010, "test author", "12345");

        assertThrows(ConstraintViolationException.class, () -> repository.save(book));
    }

    @Test
    public void shouldNotAllowLargeIsbn() {
        Book book = new Book("test title", 2010, "test author", "123456789012345");

        assertThrows(ConstraintViolationException.class, () -> repository.save(book));
    }
}
