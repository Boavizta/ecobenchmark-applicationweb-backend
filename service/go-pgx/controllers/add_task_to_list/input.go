package add_task_to_list

type requestBody struct {
	Name        string `json:"name,omitempty"`
	Description string `json:"description,omitempty"`
}
