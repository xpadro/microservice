DROP TABLE IF EXISTS BOOK;

CREATE TABLE IF NOT EXISTS BOOK (
                                    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
                                    year INT NOT NULL,
                                    author VARCHAR(100) NOT NULL,
                                    UNIQUE KEY uk_title(title)
);


INSERT INTO book (id, title, year, author) VALUES (1, 'The Lord of the Rings', 1954, 'J. R. R. Tolkien');
INSERT INTO book (id, title, year, author) VALUES (2, 'The Little Prince', 1943, 'Antoine de Saint-Exupery');
INSERT INTO book (id, title, year, author) VALUES (3, 'Harry Potter and the Philosopher Stone', 1997, 'J. J. Rowling');