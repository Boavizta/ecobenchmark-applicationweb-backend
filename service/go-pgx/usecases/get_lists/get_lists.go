package get_lists

import (
	"github.com/pkg/errors"
)

type GetListsByAccountId struct {
	Storage Storage
}

func (c *GetListsByAccountId) Execute(request GetListsRequest) ([]GetListsResponse, error) {

	response, err := c.Storage.GetListsByAccountId(request)

	if err != nil {
		return nil, errors.Wrapf(err, "failed to get the lists of account id %s", request.AccountId.String())
	}

	return response, nil
}
