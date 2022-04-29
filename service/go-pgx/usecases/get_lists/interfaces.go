package get_lists

type Storage interface {
	GetListsByAccountId(request GetListsRequest) ([]GetListsResponse, error)
}
