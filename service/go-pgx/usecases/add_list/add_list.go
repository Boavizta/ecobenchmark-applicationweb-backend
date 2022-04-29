package add_list

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"time"
)

type AddList struct {
	Storage Storage
}

func (c *AddList) Execute(request AddListRequest) (*AddListResponse, error) {
	id, err := uuid.NewV4()
	if err != nil {
		return nil, errors.Wrap(err, "failed to generate uuid")
	}

	listResponse := &AddListResponse{
		Id:           id,
		Name:         request.Name,
		AccountId:    request.AccountId,
		CreationDate: time.Now().UTC(),
	}

	err = c.Storage.AddList(listResponse)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to create the list %s", request.Name)
	}

	return listResponse, nil
}
