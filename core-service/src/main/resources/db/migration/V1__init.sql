create table if not exists products (
    id          bigserial primary key,
    title       varchar(255),
    price       numeric(8,2),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Milk', 100.99),
       ('Bread', 80.99),
       ('Beef', 100.99),
       ('Pork', 80.00),
       ('Колбаса', 100.00),
       ('Chicken', 80.00),
       ('Tuna', 100.00),
       ('Cheese', 90.00);

create table if not exists categories (
    id bigserial primary key,
    name varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
    );

insert into categories (name)
values ('Молочная продукция'),
       ('Выпечка'),
       ('Мясная продукция'),
       ('Рыбная продукция');

CREATE TABLE categories_products (
    category_id bigint not null references categories (id),
    product_id bigint not null references products (id),
    primary key (category_id, product_id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories_products (category_id, product_id)
values (1,1),
       (1,8),
       (2,2),
       (3,3),
       (3,4),
       (3,5),
       (3,6),
       (4,7);

create table if not exists orders (
    id bigserial primary key,
    username varchar(255),
    total_price numeric(8,2),
    address varchar(255),
    phone varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table order_items(
    id bigserial primary key,
    product_id bigint references products(id),
    order_id bigint references orders(id),
    quantity int not null ,
    price_per_product numeric(8,2) not null,
    price numeric(8,2) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into orders(username, total_price, address, phone) values
('bob',5400.00,'address','12345');

insert into order_items(product_id, order_id, quantity, price_per_product, price, created_at) values
(1,1,10,100.00,1000.00,'2025-03-03'),
(1,1,10,100.00,1000.00,'2025-03-03'),
(2,1,10,100.00,1000.00,'2025-03-03'),
(3,1,10,100.00,1000.00,'2025-03-03'),
(4,1,10,100.00,1000.00,'2025-03-03'),
(5,1,10,100.00,1000.00,'0001-01-01'),
(6,1,2,100.00,200.00,'2025-03-03'),
(7,1,2,100.00,200.00,'2025-03-03');
