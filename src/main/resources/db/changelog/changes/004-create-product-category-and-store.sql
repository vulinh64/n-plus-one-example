--liquibase formatted sql
--changeset vulinh:004-create-product-category-and-store

CREATE SEQUENCE product_id_seq;
CREATE SEQUENCE category_id_seq;
CREATE SEQUENCE store_id_seq;

CREATE TABLE product
(
    id   BIGINT DEFAULT nextval('product_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE category
(
    id   BIGINT DEFAULT nextval('category_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE store
(
    id   BIGINT DEFAULT nextval('store_id_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE product_category
(
    product_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    CONSTRAINT fk_product_category_product
        FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_product_category_category
        FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE product_available_store
(
    product_id BIGINT NOT NULL,
    store_id   BIGINT NOT NULL,
    PRIMARY KEY (product_id, store_id),
    CONSTRAINT fk_product_available_store_product
        FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_product_available_store_store
        FOREIGN KEY (store_id) REFERENCES store (id)
);

CREATE INDEX idx_product_category_category_id ON product_category (category_id);
CREATE INDEX idx_product_available_store_store_id ON product_available_store (store_id);

INSERT INTO product (id, name)
VALUES (1, 'Noise-Cancelling Headphones'),
       (2, 'Mechanical Keyboard'),
       (3, '4K Monitor');

INSERT INTO category (id, name)
VALUES (1, 'Electronics'),
       (2, 'Audio'),
       (3, 'Office Equipment'),
       (4, 'Computer Accessories'),
       (5, 'Displays');

INSERT INTO store (id, name)
VALUES (1, 'Bangkok Central'),
       (2, 'Chiang Mai'),
       (3, 'Phuket'),
       (4, 'Online Store'),
       (5, 'Airport Outlet');

INSERT INTO product_category (product_id, category_id)
VALUES (1, 1), (1, 2), (1, 3),
       (2, 1), (2, 3), (2, 4),
       (3, 1), (3, 3), (3, 5);

INSERT INTO product_available_store (product_id, store_id)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
       (2, 1), (2, 2), (2, 4),
       (3, 1), (3, 3), (3, 4), (3, 5);

SELECT setval('product_id_seq', (SELECT MAX(id) FROM product), true);
SELECT setval('category_id_seq', (SELECT MAX(id) FROM category), true);
SELECT setval('store_id_seq', (SELECT MAX(id) FROM store), true);

-- Product 1 produces 3 categories x 5 stores = 15 joined rows when both sets are fetched.

--rollback DROP TABLE product_available_store;
--rollback DROP TABLE product_category;
--rollback DROP TABLE store;
--rollback DROP TABLE category;
--rollback DROP TABLE product;
--rollback DROP SEQUENCE store_id_seq;
--rollback DROP SEQUENCE category_id_seq;
--rollback DROP SEQUENCE product_id_seq;
