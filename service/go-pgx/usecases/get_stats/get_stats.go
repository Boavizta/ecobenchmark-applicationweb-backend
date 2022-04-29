package get_stats

import (
	"github.com/pkg/errors"
)

type GetStatsByAccounts struct {
	Storage Storage
}

func (c *GetStatsByAccounts) Execute() ([]GetStatsByAccountsResponse, error) {
	stats, err := c.Storage.GetStatsByAccounts()
	if err != nil {
		return nil, errors.Wrapf(err, "failed to create the stats")
	}
	return stats, nil
}
