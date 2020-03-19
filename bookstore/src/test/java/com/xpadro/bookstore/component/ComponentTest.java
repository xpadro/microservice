package com.xpadro.bookstore.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.xpadro.bookstore.BookstoreApplication;
import com.xpadro.bookstore.entity.BookDetails;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookstoreApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class ComponentTest {
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String JSON_TYPE = "application/json";
    private static final int SERVICES_PORT = 8089;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(wireMockConfig()
            .port(SERVICES_PORT));


    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockRentalService();
        mockBooksService();
    }

    @Test
    public void shouldGetAllUserBooks() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/store/users/user_test/books"))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        BookDetails[] response = objectMapper.readValue(contentAsString, BookDetails[].class);

        assertThat(response.length, equalTo(1));
        assertThat(response[0].getAuthor(), equalTo("George Lucas"));
        assertThat(response[0].getYear(), equalTo(1977));
        assertThat(response[0].getTitle(), equalTo("Star Wars a new hope"));
    }

    private void mockRentalService() {
        String expectedRentals = "[" +
                "{\"id\":2, \"isbn\":\"1234567890122\", \"userId\":\"user_test\"}, " +
                "{\"id\":3, \"isbn\":\"1234567890123\", \"userId\":\"user_test\"}]";

        stubFor(WireMock.get(urlEqualTo("/rentals/user_test"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE_HEADER, JSON_TYPE)
                        .withBody(expectedRentals)));
    }

    private void mockBooksService() {
        String expectedBooks = "{\"id\":1, \"isbn\":\"1234567890122\", \"author\":\"George Lucas\", \"year\":1977, \"title\":\"Star Wars a new hope\"}";

        stubFor(WireMock.get(urlEqualTo("/books/1234567890122"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE_HEADER, JSON_TYPE)
                        .withBody(expectedBooks)));

        stubFor(WireMock.get(urlEqualTo("/books/1234567890123"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader(CONTENT_TYPE_HEADER, JSON_TYPE)));
    }
}
