--liquibase formatted sql
--changeset vulinh:003-create-customer-and-order

CREATE SEQUENCE customer_id_seq;
CREATE SEQUENCE customer_order_id_seq;

CREATE TABLE customer
(
    id    BIGINT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

ALTER SEQUENCE customer_id_seq OWNED BY customer.id;

CREATE TABLE customer_order
(
    id          BIGINT DEFAULT nextval('customer_order_id_seq') PRIMARY KEY,
    order_code  VARCHAR(50)    NOT NULL UNIQUE,
    total_price NUMERIC(12, 2) NOT NULL,
    customer_id BIGINT         NOT NULL,
    CONSTRAINT fk_customer_order_customer
        FOREIGN KEY (customer_id) REFERENCES customer (id)
);

ALTER SEQUENCE customer_order_id_seq OWNED BY customer_order.id;

CREATE INDEX idx_customer_order_customer_id ON customer_order (customer_id);

INSERT INTO customer (id, name, email)
VALUES (1, 'Alice Johnson', 'alice.johnson@example.com'),
       (2, 'Bob Smith', 'bob.smith@example.com'),
       (3, 'Carol Williams', 'carol.williams@example.com'),
       (4, 'David Brown', 'david.brown@example.com'),
       (5, 'Emma Jones', 'emma.jones@example.com'),
       (6, 'Frank Garcia', 'frank.garcia@example.com'),
       (7, 'Grace Miller', 'grace.miller@example.com'),
       (8, 'Henry Davis', 'henry.davis@example.com'),
       (9, 'Isabella Wilson', 'isabella.wilson@example.com'),
       (10, 'Jack Anderson', 'jack.anderson@example.com');

INSERT INTO customer_order (id, order_code, total_price, customer_id)
VALUES (1, 'ORD-0001', 125.50, 1),
       (2, 'ORD-0002', 89.99, 1),
       (3, 'ORD-0003', 240.00, 1),
       (4, 'ORD-0004', 49.95, 2),
       (5, 'ORD-0005', 310.25, 2),
       (6, 'ORD-0006', 175.75, 2),
       (7, 'ORD-0007', 99.00, 3),
       (8, 'ORD-0008', 450.40, 3),
       (9, 'ORD-0009', 62.30, 3),
       (10, 'ORD-0010', 520.00, 4),
       (11, 'ORD-0011', 78.80, 4),
       (12, 'ORD-0012', 145.20, 4),
       (13, 'ORD-0013', 215.10, 5),
       (14, 'ORD-0014', 35.50, 5),
       (15, 'ORD-0015', 680.00, 5),
       (16, 'ORD-0016', 120.00, 6),
       (17, 'ORD-0017', 275.45, 6),
       (18, 'ORD-0018', 58.99, 6),
       (19, 'ORD-0019', 340.75, 7),
       (20, 'ORD-0020', 410.10, 7),
       (21, 'ORD-0021', 76.25, 7),
       (22, 'ORD-0022', 199.99, 8),
       (23, 'ORD-0023', 88.50, 8),
       (24, 'ORD-0024', 560.30, 8),
       (25, 'ORD-0025', 130.40, 9),
       (26, 'ORD-0026', 225.00, 9),
       (27, 'ORD-0027', 47.75, 9),
       (28, 'ORD-0028', 390.60, 10),
       (29, 'ORD-0029', 95.20, 10),
       (30, 'ORD-0030', 710.00, 10);

SELECT setval('customer_id_seq', (SELECT MAX(id) FROM customer), true);
SELECT setval('customer_order_id_seq', (SELECT MAX(id) FROM customer_order), true);

--rollback DROP TABLE customer_order;
--rollback DROP TABLE customer;
--rollback DROP SEQUENCE IF EXISTS customer_order_id_seq;
--rollback DROP SEQUENCE IF EXISTS customer_id_seq;
