package add_account

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"time"
)

type AddAccount struct {
	Storage Storage
}

func (c *AddAccount) Execute(requestLogin string) (*AddAccountResponse, error) {
	id, err := uuid.NewV4()
	if err != nil {
		return nil, errors.Wrap(err, "failed to generate uuid")
	}

	accountResponse := &AddAccountResponse{
		Id:           id,
		Login:        requestLogin,
		CreationDate: time.Now().UTC(),
	}

	err = c.Storage.AddAccount(accountResponse)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to create the account %s", requestLogin)
	}

	return accountResponse, nil
}
