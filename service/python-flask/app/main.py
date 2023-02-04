from flask import Flask
from flask_pydantic import validate
from pydantic import BaseModel

from . import database

app = Flask(__name__)


class AccountRequest(BaseModel):
    login: str


class Pagination(BaseModel):
    page: int = 0


class ListRequest(BaseModel):
    name: str


class TaskRequest(BaseModel):
    name: str
    description: str


@app.post('/api/accounts')
@validate()
def create_account(body: AccountRequest):
    return database.create_account(login=body.login)


@app.get('/api/accounts/<account_id>/lists')
@validate()
def get_account_lists(account_id: str, query: Pagination):
    return [account_list.dict() for account_list in database.get_account_lists(account_id=account_id, page=query.page)]


@app.post('/api/accounts/<account_id>/lists')
@validate()
def create_account_lists(account_id: str, body: ListRequest):
    return database.create_account_list(account_id=account_id, name=body.name)


@app.post('/api/lists/<list_id>/tasks')
@validate()
def create_list_task(list_id: str, body: TaskRequest):
    return database.create_list_task(list_id=list_id, name=body.name, description=body.description)


@app.get('/api/stats')
@validate()
def get_stats():
    return [stat.dict() for stat in database.get_stats()]


@app.get('/healthcheck')
def get_healthcheck():
    return {}, 200
