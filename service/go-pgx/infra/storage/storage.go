package storage

import (
	"context"
	"github.com/jackc/pgx/v4/pgxpool"
	"github.com/pkg/errors"
	"go_pgx/usecases/add_account"
	"go_pgx/usecases/add_list"
	"go_pgx/usecases/add_task_to_list"
)

type Storage struct {
	ConnectionURI string
	pool          *pgxpool.Pool
}

func New(connectionURI string) (*Storage, error) {
	pool, err := pgxpool.Connect(context.Background(), connectionURI)
	if err != nil {
		return nil, errors.Wrap(err, "failed to create the connection pool")
	}

	pgStorage := &Storage{
		ConnectionURI: connectionURI,
		pool:          pool,
	}

	return pgStorage, nil
}

func (s *Storage) AddAccount(account *add_account.AddAccountResponse) error {
	_, err := s.pool.Exec(
		context.Background(),
		"INSERT INTO account(id, login, creation_date) values ($1, $2, $3);",
		account.Id,
		account.Login,
		account.CreationDate,
	)
	if err != nil {
		return errors.Wrapf(err, "failed to insert the account %s", account.Login)
	}
	return nil
}

func (s *Storage) AddList(list *add_list.AddListResponse) error {
	_, err := s.pool.Exec(
		context.Background(),
		"INSERT INTO list(id, account_id, name,  creation_date) values ($1, $2, $3, $4);",
		list.Id,
		list.AccountId,
		list.Name,
		list.CreationDate,
	)
	if err != nil {
		return errors.Wrapf(err, "failed to insert the list %s", list.Name)
	}
	return nil
}

func (s *Storage) AddTask(task *add_task_to_list.AddTaskResponse) error {
	_, err := s.pool.Exec(
		context.Background(),
		"INSERT INTO task(id, list_id, name, description, creation_date) values ($1, $2, $3, $4, $5);",
		task.Id,
		task.ListId,
		task.Name,
		task.Description,
		task.CreationDate,
	)
	if err != nil {
		return errors.Wrapf(err, "failed to insert the task %s", task.Name)
	}
	return nil
}
