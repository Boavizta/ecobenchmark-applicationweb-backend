from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
from . import database


app = FastAPI()


class AccountRequest(BaseModel):
    login: str


class ListRequest(BaseModel):
    name: str


class TaskRequest(BaseModel):
    name: str
    description: str
    

@app.post("/api/accounts")
def create_account(account_request: AccountRequest):
    return database.create_account(login=account_request.login)


@app.get("/api/accounts/{account_id}/lists")
def get_account_lists(account_id: str, page: int = 0):
    return database.get_account_lists(account_id, page)


@app.post("/api/accounts/{account_id}/lists")
def create_account_list(account_id: str, list_request: ListRequest):
    return database.create_account_list(account_id=account_id, name=list_request.name)


@app.post("/api/lists/{list_id}/tasks")
def create_list_task(list_id: str, task_request: TaskRequest):
    return database.create_list_task(list_id=list_id, name=task_request.name, description=task_request.description)


@app.get("/api/stats")
def get_stats():
    return database.get_stats()


@app.get("/healthcheck")
def healthcheck():
    pass
