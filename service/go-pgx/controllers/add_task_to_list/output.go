package add_task_to_list

type responseBody struct {
	Id           string `json:"id,omitempty"`
	Name         string `json:"name,omitempty"`
	Description  string `json:"description,omitempty"`
	CreationDate string `json:"creation_date"`
	ListId       string `json:"list_id,omitempty"`
}
