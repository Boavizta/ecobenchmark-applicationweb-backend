package get_lists

import "go_pgx/common"

type responseBody struct {
	Id           string          `json:"id,omitempty"`
	Name         string          `json:"name,omitempty"`
	Tasks        []TaskBody      `json:"tasks,omitempty"`
	CreationDate common.JSONDATE `json:"creation_date"`
	AccountId    string          `json:"account_id,omitempty"`
}

type TaskBody struct {
	Id           string          `json:"id,omitempty"`
	Name         string          `json:"name,omitempty"`
	Description  string          `json:"description,omitempty"`
	CreationDate common.JSONDATE `json:"creation_date"`
}
