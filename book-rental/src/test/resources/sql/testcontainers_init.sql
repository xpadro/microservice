USE database_integration_test;

CREATE TABLE IF NOT EXISTS rental (
                                    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    isbn VARCHAR(13) NOT NULL,
                                    userid VARCHAR(100) NOT NULL,
                                    UNIQUE KEY uk_isbn(isbn),
                                    UNIQUE KEY uk_userid(userid)
);


INSERT IGNORE INTO rental (id, isbn, userid) VALUES (1, '1234567890121', 'user_1');
INSERT IGNORE INTO rental (id, isbn, userid) VALUES (2, '1234567890122', 'user_2');