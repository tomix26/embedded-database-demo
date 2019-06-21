create extension if not exists ltree with schema test;

create table test.person (
  id         bigint primary key not null,
  first_name varchar(255)       not null,
  last_name  varchar(255)       not null,
  okm_path   test.ltree
);

insert into test.person (id, first_name, last_name, okm_path) values (1, 'Dave', 'Syer', 'ROOT.first.parent');