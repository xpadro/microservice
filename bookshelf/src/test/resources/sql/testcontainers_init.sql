USE database_integration_test;

CREATE TABLE IF NOT EXISTS book (
                                    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
                                    year INT NOT NULL,
                                    author VARCHAR(100) NOT NULL,
                                    isbn VARCHAR(13) NOT NULL,
                                    UNIQUE KEY uk_title(title),
                                    UNIQUE KEY uk_isbn(isbn)
);


INSERT IGNORE INTO book (id, title, year, author, isbn) VALUES (1, 'The Lord of the Rings', 1954, 'J. R. R. Tolkien', '1234567890123');
INSERT IGNORE INTO book (id, title, year, author, isbn) VALUES (2, 'The Little Prince', 1943, 'Antoine de Saint-Exupery', '1234567890124');
INSERT IGNORE INTO book (id, title, year, author, isbn) VALUES (3, 'Harry Potter and the Philosopher Stone', 1997, 'J. J. Rowling', '1234567890125');