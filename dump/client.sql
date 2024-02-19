create table client
(
    id          integer default nextval('"Client_id_seq"'::regclass) not null
        constraint client_pk
            primary key,
    name        varchar(40),
    contactinfo varchar(40)
);

alter table client
    owner to postgres;

INSERT INTO public.client (id, name, contactinfo) VALUES (4, 'cata', 'jafl');
INSERT INTO public.client (id, name, contactinfo) VALUES (6, 'cata', 'jafl');
INSERT INTO public.client (id, name, contactinfo) VALUES (7, 'cata', 'jafl');
INSERT INTO public.client (id, name, contactinfo) VALUES (8, 'cata', 'jafl1');
INSERT INTO public.client (id, name, contactinfo) VALUES (12, 'cata2', 'jag]sdhl2');
INSERT INTO public.client (id, name, contactinfo) VALUES (13, 'client_nou', 'contact_nou');
INSERT INTO public.client (id, name, contactinfo) VALUES (14, 'alex', 'zeu@yahoo.com');
INSERT INTO public.client (id, name, contactinfo) VALUES (16, 'alin', 'acasa');
INSERT INTO public.client (id, name, contactinfo) VALUES (1, 'cata_first', 'tera');
