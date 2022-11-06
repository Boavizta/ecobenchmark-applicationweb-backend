package add_account

import "go_pgx/common"

type responseBody struct {
	Id           string          `json:"id,omitempty"`
	Login        string          `json:"login,omitempty"`
	CreationDate common.JSONDATE `json:"creation_date"`
}
