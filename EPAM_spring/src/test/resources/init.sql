DELETE FROM chequeLines;
DELETE FROM users;
DELETE FROM products;
DELETE FROM cheques;

INSERT INTO users (role, password, username) VALUES
(1, 'abcd', 'Cas'),
(2, '1234', 'SeC'),
(3, '0000', 'Mer');

INSERT INTO products (code, price, totalAmount, countable, removed, title) VALUES
(1, 300, 235, true, false, 'Milk'),
(2, 450, 10000, false, false, 'Cheese'),
(3, 600, 400, true, false, 'Butter'),
(4, 250, 290, true, false, 'Egg'),
(5, 400, 70000, false, false, 'Pork'),
(6, 1050, 2000, false, false, 'Salmon'),
(7, 400, 255000, false, false, 'Potato'),
(8, 100, 85000, false, false, 'Carrot'),
(9, 120, 65000, false, false, 'Onion'),
(10, 200, 125, true, false, 'Soap'),
(11, 340, 55000, false, false, 'Sugar'),
(12, 120, 10000, false, false, 'Salt'),
(13, 200, 200, true, false, 'Foil');


INSERT INTO cheques (id, price, state, creationTime) VALUES
(1, 0, 1, DATE '2021-12-12'),
(2, 0, 1, DATE '2019-09-14'),
(3, 0, 1, DATE '2020-03-09'),
(4, 0, 1, DATE '2021-07-22'),
(5, 0, 1, DATE '2018-09-21'),
(6, 0, 1, DATE '2021-01-16'),
(7, 0, 1, DATE '2021-03-18'),
(8, 0, 1, DATE '2020-10-08'),
(9, 0, 1, DATE '2021-11-11'),
(10, 0, 1, DATE '2017-08-12'),
(11, 0, 1, DATE '2019-09-27'),
(12, 0, 1, DATE '2017-11-22');

INSERT INTO chequeLines (id, chequeId, productCode, amount) VALUES
(1, 1, 1, 2),
(2, 1, 2, 200),
(3, 1, 3, 1),
(4, 1, 4, 1),

(5, 2, 5, 2000),
(6, 2, 6, 500),

(7, 3, 7, 1000),
(8, 3, 8, 1000),
(9, 3, 9, 2000),

(10, 4, 10, 2),
(11, 4, 11, 200),
(12, 4, 12, 1000),
(13, 4, 13, 1),

(14, 5, 1, 2),
(15, 5, 2, 200),
(16, 5, 3, 3),
(17, 5, 4, 2),
(18, 5, 5, 1000),

(19, 6, 6, 500),
(20, 6, 7, 200),
(21, 6, 8, 1500),


(22, 7, 9, 2500),
(23, 7, 10, 2),
(24, 7, 11, 1000),
(25, 7, 12, 4000),
(26, 7, 13, 3),
(27, 7, 1, 1),
(28, 7, 2, 200),

(29, 8, 3, 2),
(30, 8, 4, 1),
(31, 8, 5, 400),
(32, 8, 6, 1500),

(33, 9, 7, 2500),
(34, 9, 8, 1400),
(35, 9, 9, 1000),
(36, 9, 10, 1),
(37, 9, 11, 1200),

(38, 10, 12, 2900),
(39, 10, 13, 5),

(40, 11, 1, 2),
(41, 11, 2, 1200),
(42, 11, 3, 1),
(43, 11, 4, 1),

(44, 12, 5, 6000);