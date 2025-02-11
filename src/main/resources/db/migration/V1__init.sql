create table if not exists products (
    id          bigserial primary key,
    title       varchar(255),
    price       int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into products (title, price)
values ('Milk', 100),
       ('Bread', 80),
       ('Beef', 100),
       ('Pork', 80),
       ('Колбаса', 100),
       ('Chicken', 80),
       ('Tuna', 100),
       ('Cheese', 90);

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

create table users (
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles (
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles (
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

create table if not exists orders (
    id bigserial primary key,
    user_id bigint not null references users(id),
    total_price int,
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
    price_per_product int not null,
    price int not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into orders(user_id, total_price, address, phone) values
(1,200,'address','12345');

insert into order_items(product_id, order_id, quantity, price_per_product, price) values
(1,1,2,100,200);
