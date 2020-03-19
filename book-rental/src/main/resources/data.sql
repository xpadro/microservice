DROP TABLE IF EXISTS RENTAL;

CREATE TABLE RENTAL (
                      id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      isbn VARCHAR(13) NOT NULL,
                      userid VARCHAR(100) NOT NULL,
                      UNIQUE KEY uk_isbn(isbn)
);


INSERT INTO RENTAL (id, isbn, userid) VALUES (1, '1234567890121', 'user_1');
INSERT INTO RENTAL (id, isbn, userid) VALUES (2, '1234567890122', 'user_2');
INSERT INTO RENTAL (id, isbn, userid) VALUES (3, '1234567890123', 'user_2');