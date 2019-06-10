create extension if not exists ltree with schema test;

create table test.person (
  id         bigint primary key not null,
  first_name varchar(255)       not null,
  last_name    varchar(255)       not null
);

insert into test.person (id, first_name, last_name) values (1, 'Dave', 'Syer');