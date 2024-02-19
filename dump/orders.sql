create table orders
(
    id         integer default nextval('"Order_id_seq"'::regclass) not null
        constraint orders_pk
            primary key,
    "prodId"   integer,
    "clientId" integer,
    quantity   integer
);

alter table orders
    owner to postgres;

INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (1, 1, 1, 5);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (2, 1, 1, 20);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (3, 1, 1, 20);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (4, 4, 4, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (5, 4, 4, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (6, 1, 12, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (7, 8, 8, 3);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (8, 8, 8, 3);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (9, 5, 1, 2);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (10, 8, 1, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (11, 8, 1, 2);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (12, 4, 13, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (13, 5, 7, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (14, 6, 4, 1);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (15, 5, 4, 3);
INSERT INTO public.orders (id, "prodId", "clientId", quantity) VALUES (16, 10, 4, 1);
