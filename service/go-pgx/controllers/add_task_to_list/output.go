package add_task_to_list

import "go_pgx/common"

type responseBody struct {
	Id           string          `json:"id,omitempty"`
	Name         string          `json:"name,omitempty"`
	Description  string          `json:"description,omitempty"`
	CreationDate common.JSONDATE `json:"creation_date"`
	ListId       string          `json:"list_id,omitempty"`
}
