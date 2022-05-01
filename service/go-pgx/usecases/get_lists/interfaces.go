package get_lists

import "github.com/gofrs/uuid"

type Storage interface {
	GetListsByAccountId(request GetListsRequest) ([]GetListsResponse, error)
	GetTaskByListId(listId uuid.UUID) ([]TasksResponse, error)
}
