from __future__ import annotations
import datetime
from uuid import uuid4
from typing import List
from sqlalchemy import create_engine, text
from sqlalchemy.orm import Session
from pydantic import BaseModel
import os

# DATABASE_URL = "postgresql://postgres:mysecretpassword@127.0.0.1:5432/postgres"
DATABASE_URL = os.getenv("DATABASE_URL")
# DATABASE_POOL_SIZE = 20
DATABASE_POOL_SIZE = os.getenv("DATABASE_POOL_SIZE")
engine = create_engine(DATABASE_URL, pool_size=int(DATABASE_POOL_SIZE))

class AccountResponse(BaseModel):
    id: str
    login: str
    creation_date: str


class AccountStatistics(BaseModel):
    account_id: str
    account_login: str
    list_count: int
    task_avg: int 


class TaskResponse(BaseModel):
    id: str
    name: str
    description: str
    creation_date: str


class ListResponse(BaseModel):
    id: str
    name: str
    account_id: str
    creation_date: str
    tasks: List[TaskResponse]


def create_account(login: str):
    id = str(uuid4())
    creation_date = str(datetime.datetime.now())
    session = Session(engine)
    session.execute(
        text("INSERT INTO account (id, login, creation_date) VALUES (:id, :login, :creation_date)"),
        [{"id": id, "login": login, "creation_date": creation_date}]
    )
    session.commit()
    return AccountResponse(id=id, login=login, creation_date=creation_date)


def get_account_lists(account_id: str, page: int):
    limit = 10
    session = Session(engine)
    rows = session.execute(
        text("""
        SELECT l.id, l.name, l.creation_date, l.account_id, t.id AS task_id,
               t.name AS task_name, t.description, t.creation_date AS task_creation_date
        FROM list l
        LEFT JOIN task t ON l.id = t.list_id 
        WHERE l.account_id = :account_id
        AND l.id IN (
            SELECT id
            FROM list
            WHERE account_id = :account_id
            LIMIT :limit
            OFFSET :offset
        )
        """),
        [{"account_id": account_id, "limit": limit, "offset": limit * page}]
    )
    lists = {}
    for row in rows:
        if row.id not in lists.keys():
            lists[row.id] = ListResponse(
                id=str(row.id),
                name=row.name,
                tasks=[],
                creation_date=str(row.creation_date),
                account_id=str(row.account_id)
            )
        if row.task_id is not None:
            lists[row.id].tasks.append(TaskResponse(
                id=str(row.task_id),
                name=row.task_name,
                description=row.description,
                creation_date=str(row.task_creation_date)
            ))
    session.commit()
    return list(lists.values())


def create_account_list(account_id: str, name: str):
    id = str(uuid4())
    creation_date = str(datetime.datetime.now())
    session = Session(engine)
    session.execute(
        text("INSERT INTO list(id, account_id, name,  creation_date) values (:id, :account_id, :name, :creation_date)"),
        [{"id": id, "account_id": account_id, "name": name, "creation_date": creation_date}]
    )
    session.commit()
    return ListResponse(id=id, account_id=account_id, name=name, creation_date=creation_date, tasks=[])

    
def create_list_task(list_id: str, name: str, description: str):
    id = str(uuid4())
    creation_date = str(datetime.datetime.now())
    session = Session(engine)
    session.execute(
        text("INSERT INTO task(id, list_id, name, description, creation_date) values (:id, :list_id, :name, :description, :creation_date)"),
        [{"id": id, "list_id": list_id, "name": name, "description": description, "creation_date": creation_date}]
    )
    session.commit()
    return TaskResponse(id=id, list_id=list_id, name=name, description=description, creation_date=creation_date)   


def get_stats():
    session = Session(engine)
    rows = session.execute(
        text("""
        SELECT id, login, count(list_id) as nb_list, round(avg(nb_tasks), 2) as avg_tasks
        FROM
        (
            SELECT account.id, account.login, list.id list_id, count(task.id) nb_tasks
            FROM account
            INNER JOIN list on (list.account_id = account.id)
            LEFT JOIN task on (task.list_id = list.id)
            GROUP BY account.id, account.login, list.id
        ) t
        GROUP BY id, login
        """)
    )
    session.commit()
    return [
        AccountStatistics(
            account_id=str(row.id),
           account_login=row.login,
            list_count=row.nb_list,
            task_avg=row.avg_tasks,
        ) 
        for row in rows
    ]
    