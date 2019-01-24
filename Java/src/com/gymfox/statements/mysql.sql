CREATE DATABASE shop;

USE shop;

CREATE USER 'shop'@'localhost' IDENTIFIED BY 'pass';

GRANT ALL PRIVILEGES ON shop.* TO 'shop'@'localhost';

CREATE USER 'viewer'@'localhost' IDENTIFIED BY 'pass';

GRANT SELECT ON shop.* TO 'viewer'@'localhost';

FLUSH PRIVILEGES;

CREATE TABLE category (
    id_category INT AUTO_INCREMENT,
    category_title VARCHAR(30) NOT NULL, 
    PRIMARY KEY(id_category)
);

INSERT INTO category (category_title) VALUES ('Smartphones'), ('Tabs'), ('TV');

CREATE TABLE product (
    id_product INT AUTO_INCREMENT,
    product_title VARCHAR(255) NOT NULL, 
    product_price double NOT NULL,
    id_category INT NOT NULL,
    PRIMARY KEY(id_product)
);

ALTER TABLE product ADD CONSTRAINT fk_product_category FOREIGN KEY (id_category) REFERENCES category (id_category);

INSERT INTO product (product_title, product_price, id_category) VALUES 
    ('redmi 4x', '1.00', 1),
    ('redmi a5', '1.00', 1),
    ('iphone', '1.00', 1), 
    ('ipad', '1.00', 2), 
    ('MiPad 4', '1.00', 2), 
    ('AsusPad', '2.00', 2), 
    ('Samsung SmartTV', '3.22', 3), 
    ('LG SmartTv', '22.8', 3);

UPDATE product SET product_price='3.50' WHERE id_product=1;

UPDATE product SET product_price=(SELECT product_price) * 1.1;

DELETE FROM product WHERE id_product = 2;

SELECT product_title FROM product ORDER BY product_title;

SELECT product_price FROM product ORDER BY product_price DESC;

SELECT product_price FROM product ORDER BY product_price DESC LIMIT 3;

SELECT product_price FROM product ORDER BY product_price LIMIT 3;

SELECT product_price FROM product ORDER BY product_price DESC LIMIT 3, 3;

SELECT product_price FROM product WHERE product_price = (SELECT MAX(product_price) FROM product);

SELECT product_price FROM product WHERE product_price = (SELECT MIN(product_price) FROM product);

SELECT COUNT(product_title) FROM product;

SELECT AVG(product_price) FROM product;

CREATE VIEW expensive_product AS SELECT * FROM product ORDER BY product_price DESC LIMIT 3;
