package com.xpadro.bookshelf.service;

import com.xpadro.bookshelf.entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book find(Long id);
}
