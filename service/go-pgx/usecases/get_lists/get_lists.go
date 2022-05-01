package get_lists

import (
	"github.com/pkg/errors"
)

type GetListsByAccountId struct {
	Storage Storage
}

func (c *GetListsByAccountId) Execute(request GetListsRequest) ([]GetListsResponse, error) {

	response, err := c.Storage.GetListsByAccountId(request)

	for index, list := range response {
		tasks, errTasks := c.Storage.GetTaskByListId(list.Id)
		if errTasks == nil {
			response[index].Tasks = tasks
		}
	}

	if err != nil {
		return nil, errors.Wrapf(err, "failed to get the lists of account id %s", request.AccountId.String())
	}

	return response, nil
}
