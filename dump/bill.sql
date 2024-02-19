create table bill
(
    customer_id integer,
    product     varchar(40),
    quantity    integer,
    totalprice  double precision
);

alter table bill
    owner to postgres;

INSERT INTO public.bill (customer_id, product, quantity, totalprice) VALUES (1, 'paine', 20, 2.2);
INSERT INTO public.bill (customer_id, product, quantity, totalprice) VALUES (1, 'paine', 20, 2.2);
INSERT INTO public.bill (customer_id, product, quantity, totalprice) VALUES (4, 'papa', 1, 20.1);
INSERT INTO public.bill (customer_id, product, quantity, totalprice) VALUES (4, 'mancare', 3, 2.2);
INSERT INTO public.bill (customer_id, product, quantity, totalprice) VALUES (4, 'zahar', 1, 2.3);
