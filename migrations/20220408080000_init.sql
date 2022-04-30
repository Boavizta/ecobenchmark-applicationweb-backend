CREATE TABLE account
(
    id            uuid primary key default gen_random_uuid(),
    login         text,
    creation_date timestamp default now()
);

CREATE INDEX idx_account_creation_date
    ON account(creation_date);

CREATE TABLE list
(
    id            uuid primary key default gen_random_uuid(),
    account_id    uuid references account(id),
    name          text,
    creation_date timestamp default now()
);


CREATE INDEX idx_list_account_id
    ON list(account_id);

CREATE INDEX idx_list_creation_date
    ON list(creation_date);

CREATE TABLE task
(
    id            uuid primary key default gen_random_uuid(),
    list_id       uuid references list(id),
    name          text,
    description   text,
    creation_date timestamp default now()
);

CREATE INDEX idx_task_list_id
    ON task(list_id);

CREATE INDEX idx_task_creation_date
    ON task(creation_date);