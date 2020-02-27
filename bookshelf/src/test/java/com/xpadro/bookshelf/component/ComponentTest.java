package com.xpadro.bookshelf.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpadro.bookshelf.BookshelfApplication;
import com.xpadro.bookshelf.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookshelfApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class ComponentTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnTheRequestedBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Book result = objectMapper.readValue(contentAsString, Book.class);

        assertThat(result.getTitle(), equalTo("The Lord of the Rings"));
    }
}
