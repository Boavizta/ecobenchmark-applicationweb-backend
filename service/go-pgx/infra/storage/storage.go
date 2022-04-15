package storage

import (
	"context"
	"github.com/jackc/pgx/v4/pgxpool"
	"github.com/pkg/errors"
	"go_pgx/usecases/add_account"
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
