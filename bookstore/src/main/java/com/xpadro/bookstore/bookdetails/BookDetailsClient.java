package com.xpadro.bookstore.bookdetails;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookshelf-service")
public interface BookDetailsClient {

    @GetMapping(value = "/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    BookDetails getBook(@PathVariable String isbn);
}
