package com.xpadro.bookshelf.service;

import com.xpadro.bookshelf.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
}
