package get_lists

type responseBody struct {
	Id           string     `json:"id,omitempty"`
	Name         string     `json:"name,omitempty"`
	Tasks        []TaskBody `json:"tasks,omitempty"`
	CreationDate string     `json:"creation_date"`
	AccountId    string     `json:"account_id,omitempty"`
}

type TaskBody struct {
	Id           string `json:"id,omitempty"`
	Name         string `json:"name,omitempty"`
	Description  string `json:"description,omitempty"`
	CreationDate string `json:"creation_date"`
}
