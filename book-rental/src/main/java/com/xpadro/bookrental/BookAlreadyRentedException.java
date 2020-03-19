package com.xpadro.bookrental;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAlreadyRentedException extends RuntimeException {

    public BookAlreadyRentedException(String msg) {
        super(msg);
    }
}
