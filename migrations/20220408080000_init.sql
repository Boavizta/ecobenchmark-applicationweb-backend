/* UUID in MYSQL https://stitcher.io/blog/optimised-uuids-in-mysql */

CREATE TABLE account
(
    id            binary(16) primary key DEFAULT (UUID_TO_BIN(uuid())),
    login         text,
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_account_creation_date
    ON account(creation_date);

CREATE TABLE list
(
    id            binary(16) primary key DEFAULT (UUID_TO_BIN(uuid())),
    account_id    binary(16),
    name          text,
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX idx_list_account_id
    ON list(account_id);

CREATE INDEX idx_list_creation_date
    ON list(creation_date);

CREATE TABLE task
(
    id            binary(16) primary key DEFAULT (UUID_TO_BIN(uuid())),
    list_id       binary(16),
    name          text,
    description   text,
    creation_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_task_list_id
    ON task(list_id);

CREATE INDEX idx_task_creation_date
    ON task(creation_date);