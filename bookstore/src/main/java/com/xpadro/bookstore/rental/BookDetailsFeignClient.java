package com.xpadro.bookstore.rental;

import com.xpadro.bookstore.entity.BookDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookshelf-service", url = "http://localhost:8082")
public interface BookDetailsFeignClient {

    @GetMapping(value = "/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDetails getBook(@PathVariable String isbn);
}
