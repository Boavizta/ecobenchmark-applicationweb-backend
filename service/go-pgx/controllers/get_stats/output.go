package get_stats

type responseBody struct {
	AccountId    string  `json:"account_id,omitempty"`
	AccountLogin string  `json:"account_login,omitempty"`
	ListCount    int64   `json:"list_count,omitempty"`
	TaskAvg      float64 `json:"task_avg"`
}
