CREATE DATABASE shop;

CREATE USER shop;

GRANT ALL PRIVILEGES ON DATABASE shop TO shop;

CREATE USER viewer;

CREATE TABLE category (
    id_category SERIAL PRIMARY KEY,
    category_title TEXT NOT NULL
);


CREATE TABLE product ( 
    id_product SERIAL PRIMARY KEY, 
    product_title TEXT NOT NULL, 
    product_price NUMERIC NOT NULL,
    id_category INTEGER
);

GRANT SELECT ON category, product TO viewer;

INSERT INTO category (category_title) VALUES 
    ('Smartphones'), 
    ('Tabs'),
    ('TV'),
    ('Watches');

INSERT INTO product (product_title, product_price, id_category) VALUES
    ('Redmi', '1.0', 1),
    ('IPhone', '1.0', 1),
    ('Samsung', '1.0', 1),
    ('IPad', '2.54', 2),
    ('MiPad', '3.25', 2),
    ('LG', '10.23', 3),
    ('Samsung Smart TV', '15.47', 3),
    ('Asus Pad', '4.2', 2),
    ('Lenovo', '0.5', 2);

UPDATE product SET product_price = '3.50' WHERE id_product = 1;

UPDATE product SET product_price = (SELECT product_price) * 1.1;

DELETE FROM product WHERE id_product = 2;

SELECT * FROM product ORDER BY product_title;

SELECT * FROM product ORDER BY product_price DESC;

SELECT * FROM product ORDER BY product_price DESC LIMIT 3;

SELECT * FROM product ORDER BY product_price LIMIT 3;

SELECT * FROM product ORDER BY product_price DESC LIMIT 3 OFFSET 3;

SELECT * FROM product WHERE product_price = (SELECT MAX(product_price) FROM product);

SELECT * FROM product WHERE product_price = (SELECT MIN(product_price) FROM product);

SELECT COUNT (product_title) FROM product;

SELECT AVG (product_price) FROM product;

CREATE VIEW expensive_product AS SELECT * FROM product ORDER BY product_price DESC LIMIT 3;
