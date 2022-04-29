package add_list

type responseBody struct {
	Id           string `json:"id,omitempty"`
	Name         string `json:"name,omitempty"`
	CreationDate string `json:"creation_date"`
	AccountId    string `json:"account_id,omitempty"`
}
