package add_list

type Storage interface {
	AddList(list *AddListResponse) error
}
