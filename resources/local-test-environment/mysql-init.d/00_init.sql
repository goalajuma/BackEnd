CREATE
    USER 'golajuma-test'@'localhost' IDENTIFIED BY 'golajuma-test';
CREATE
    USER 'golajuma-test'@'%' IDENTIFIED BY 'golajuma-test';

GRANT ALL PRIVILEGES ON *.* TO
    'golajuma-test'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
    'golajuma-test'@'%';

CREATE
    DATABASE golajuma_test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
