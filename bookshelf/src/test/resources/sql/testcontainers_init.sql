USE database_integration_test;

CREATE TABLE IF NOT EXISTS book (
                                    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
                                    year INT NOT NULL,
                                    author VARCHAR(100) NOT NULL,
                                    UNIQUE KEY uk_title(title)
);


INSERT IGNORE INTO book (id, title, year, author) VALUES (1, "The Lord of the Rings", 1954, "J. R. R. Tolkien");
INSERT IGNORE INTO book (id, title, year, author) VALUES (2, "The Little Prince", 1943, "Antoine de Saint-Exupery");
INSERT IGNORE INTO book (id, title, year, author) VALUES (3, "Harry Potter and the Philosopher's Stone", 1997, "J. J. Rowling");