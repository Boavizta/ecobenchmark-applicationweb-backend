package add_list

import "go_pgx/common"

type responseBody struct {
	Id           string          `json:"id,omitempty"`
	Name         string          `json:"name,omitempty"`
	CreationDate common.JSONDATE `json:"creation_date"`
	AccountId    string          `json:"account_id,omitempty"`
}
