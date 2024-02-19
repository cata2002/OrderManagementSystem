create table product
(
    id          integer default nextval('"Product_id_seq"'::regclass) not null
        constraint product_pk
            primary key,
    prodname    varchar(40)
        constraint product_pk1
            unique,
    description varchar(40),
    price       double precision,
    stock       integer
);

alter table product
    owner to postgres;

INSERT INTO public.product (id, prodname, description, price, stock) VALUES (7, 'new_prod', 'very_new', 12.1, 10);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (8, 'yess', 'da', 2.3, 2);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (6, 'papa', 'yummy', 20.1, 2);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (4, 'ceva_ieftin', 'da', 3, 6);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (12, 'ceva_nou', 'shiny', 2.2, 14);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (2, 'altceva', 'great', 6.9, 12);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (10, 'zahar', 'dulce', 2.3, 8);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (13, 'mancare_buna', 'yummm', 12.2, 20);
INSERT INTO public.product (id, prodname, description, price, stock) VALUES (1, 'paine', 'alba', 5.2, 10);
