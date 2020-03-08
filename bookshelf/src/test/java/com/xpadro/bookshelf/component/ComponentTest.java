package com.xpadro.bookshelf.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpadro.bookshelf.BookshelfApplication;
import com.xpadro.bookshelf.entity.Book;
import com.xpadro.bookshelf.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookshelfApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Sql(scripts = "/sql/h2_init.sql")
public class ComponentTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void shouldReturnTheRequestedBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book result = objectMapper.readValue(contentAsString, Book.class);

        assertThat(result.getTitle(), equalTo("The Lord of the Rings"));
    }

    @Test
    public void shouldReturnAllBooks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<?> result = objectMapper.readValue(contentAsString, List.class);

        assertThat(result.size(), equalTo(3));
    }

    @Test
    public void shouldCreateANewBook() throws Exception {
        Book newBook = new Book("test book 6", 1990, "test author", "1234567890126");
        MvcResult mvcResult = mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(newBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mvcResult.getResponse().getHeader("location"), equalTo("http://localhost/books/4"));

        Optional<Book> savedBook = bookRepository.findById(4L);
        if (!savedBook.isPresent()) {
            fail("book should be saved");
        } else {
            assertThat(savedBook.get().getTitle(), equalTo(newBook.getTitle()));
        }
    }

    @Test
    public void shouldNotCreateBookWithShortTitle() throws Exception {
        Book newBook = new Book("a", 1990, "test author", "1234567890123");

        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(newBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateBookWithShortIsbn() throws Exception {
        Book newBook = new Book("test title", 1990, "test author", "1234567");

        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(newBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotCreateBookWithLargeIsbn() throws Exception {
        Book newBook = new Book("test title", 1990, "test author", "1234567890123456");

        mockMvc.perform(post("/books")
                .content(objectMapper.writeValueAsString(newBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
