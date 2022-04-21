package get_stats

type Storage interface {
	GetStatsByAccounts() ([]GetStatsByAccountsResponse, error)
}
