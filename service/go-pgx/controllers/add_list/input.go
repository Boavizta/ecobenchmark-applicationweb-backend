package add_list

type requestBody struct {
	Name      string `json:"name,omitempty"`
	AccountId string `json:"account_id,omitempty"`
}
