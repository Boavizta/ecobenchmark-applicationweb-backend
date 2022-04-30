DROP SCHEMA IF EXISTS ecobenchmark CASCADE;
CREATE SCHEMA ecobenchmark;

SET search_path TO ecobenchmark;

CREATE TABLE account
(
    id            uuid primary key,
    login         text,
    creation_date timestamp
);

CREATE TABLE list
(
    id            uuid primary key,
    account_id    uuid references account(id),
    name          text,
    creation_date timestamp
);

CREATE TABLE task
(
    id            uuid primary key,
    list_id       uuid references list(id),
    name          text,
    description   text,
    creation_date timestamp
);