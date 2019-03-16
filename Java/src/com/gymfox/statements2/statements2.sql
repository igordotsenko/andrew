-- 1 Вывести все товары и категорию, в которой они находятся.
SELECT "item".item_id, "item".item_name, "category".category_title FROM "item" 
NATURAL JOIN "category";

-- 2 Вывести все товары из конкретного заказа.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
WHERE "item__order".order_id = ?;

-- 3 Вывести все заказы с конкретной единицей товара.
SELECT "order".* FROM "item__order" 
NATURAL JOIN "order"
WHERE "item__order".item_id = ?;

-- 4 Вывести все товары, заказанные за последний час.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
NATURAL JOIN "order"
WHERE "order".order_created >= now() - interval '1 hour';

-- 5 Вывести все товары, заказанные за сегодня.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
NATURAL JOIN "order" 
WHERE "order".order_created >= now()::date;

-- 6 Вывести все товары, заказанные за вчера.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
NATURAL JOIN "order"
WHERE "order".order_created >= now()::date - 1;

-- 7 Вывести все товары из заданной категории, заказанные за последний час.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
NATURAL JOIN "order"
WHERE "order".order_created >= now() - interval '1 hour' AND "item".category_id = ?;

-- 8 Вывести все товары из заданной категории, заказанные за сегодня.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
NATURAL JOIN "order"
WHERE "order".order_created >= now()::date AND "item".category_id = ?;

-- 9 Вывести все товары из заданной категории, заказанные за вчера.
SELECT "item".* FROM "item__order" 
NATURAL JOIN "item"
NATURAL JOIN "order"
WHERE "order".order_created >= now()::date - 1 AND "item".category_id = ?;

-- 10 Вывести все товары, названия которых начинаются с заданной последовательности букв (см. LIKE).
SELECT * FROM "item" WHERE lower(item_name) LIKE '?%';

-- 11 Вывести все товары, названия которых заканчиваются заданной последовательностью букв (см. LIKE).
SELECT * FROM "item" WHERE lower(item_name) LIKE '%?';

-- 12 Вывести все товары, названия которых содержат заданные последовательности букв (см. LIKE).
SELECT * FROM "item" WHERE lower(item_name) LIKE '%?%'

-- 13 Вывести список категорий и количество товаров в каждой категории.
SELECT "category".category_title, COUNT(*) AS "item_count" FROM "item" 
NATURAL JOIN "category"
GROUP BY category.category_id;

-- 14 Вывести список всех заказов и количество товаров в каждом.
SELECT "order".*, COUNT(*) FROM "order" 
NATURAL JOIN "item__order"
GROUP BY "order".order_id;

-- 15 Вывести список всех товаров и количество заказов, в которых имеется этот товар.
SELECT "item".item_id, "item".item_name, "item".item_price, COUNT(*) AS "order_count" FROM "item" 
NATURAL JOIN "item__order" 
GROUP BY "item".item_id;

-- 16 Вывести список заказов, упорядоченный по дате заказа и суммарную стоимость товаров в каждом из них.
SELECT "order".*, SUM ("item__order".item__order_quantity * "item".item_price) AS Total_price FROM "item__order" 
NATURAL JOIN "item" 
NATURAL JOIN "order" 
GROUP BY "order".order_id 
ORDER BY "order".order_created;

-- 17 Вывести список товаров, цену, количество и суммарную стоимость каждого из них в заказе с заданным ID.
SELECT "item".item_name, "item".item_price, "item__order".item__order_quantity, "item__order".item__order_quantity * "item".item_price AS total_sum FROM "item"
NATURAL JOIN "item__order"
WHERE "item__order".order_id = ?;

-- 18 Для заданного ID заказа вывести список категорий, товары из которых присутствуют в этом заказе. Для каждой из категорий вывести суммарное количество и суммарную стоимость товаров.
SELECT "category".category_title, SUM(item__order.item__order_quantity), SUM(item__order.item__order_quantity * item.item_price) 
FROM "category", "item" 
NATURAL JOIN "item__order" 
WHERE "item__order".order_id = 1 AND "item".category_id = "category".category_id
GROUP BY "category".category_id;

-- 19 Вывести список клиентов, которые заказывали товары из категории с заданным ID за последние 3 дня.
SELECT "customer".customer_id, "customer".customer_name, "order".order_id, "order".order_address, "order".order_description, "order".order_created 
FROM "item__order" 
NATURAL JOIN "item" 
NATURAL JOIN "order"
NATURAL JOIN "customer" 
WHERE item.category_id = 1 AND "order".order_created > now()::date - 3 
GROUP BY "customer".customer_id, "order".order_id, "order".order_address, "order".order_description, "order".order_created;

-- 20. Вывести имена всех клиентов, производивших заказы за последние сутки.
SELECT customer.customer_name FROM "customer" 
NATURAL JOIN "order" 
WHERE "order".order_created > now() - interval '24 hours';

-- 21. Вывести всех клиентов, производивших заказы, содержащие товар с заданным ID.
SELECT "customer".* FROM "customer" 
NATURAL JOIN "order" 
NATURAL JOIN "item__order" 
WHERE "item__order".item_id = ?;

-- 22 Для каждой категории вывести урл загрузки изображения с именем category_image в формате 'http://img.domain.com/category/<category_id>.jpg' для включенных категорий, и 'http://img.domain.com/category/<category_id>_disabled.jpg' для выключеных.
SELECT "category".*,
CASE "category".category_enabled WHEN TRUE
    THEN format ('http://img.domain.com/category/<category_id>.jpg', "category".category_id)
    ELSE format ('http://img.domain.com/category/<category_id>_disabled.jpg', "category".category_id)
    END
AS "category_image" FROM "category";

-- 23 Для товаров, которые были заказаны за все время во всех заказах общим количеством более X единиц, установить item_popular = TRUE, для остальных — FALSE.
UPDATE "item" SET item_popular = CASE  
    WHEN res.total_quantity > 1 THEN TRUE
    ELSE FALSE
END
FROM ( 
    SELECT "item".item_id, SUM("item__order_quantity") AS "total_quantity"
    FROM "item"
    NATURAL JOIN "item__order"
    GROUP BY "item_id") "res"
WHERE "item".item_id = res.item_id;

-- 24 Одним запросом для указанных ID категорий установить флаг category_enabled = TRUE, для остальных — FALSE. Не применять WHERE.
UPDATE "category" SET "category_enabled" = "category_id" IN (4);
