package add_account

type responseBody struct {
	Id           string `json:"id,omitempty"`
	Login        string `json:"name,omitempty"`
	CreationDate string `json:"creation_date"`
}
