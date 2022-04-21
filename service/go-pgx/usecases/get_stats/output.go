package get_stats

import (
	"github.com/gofrs/uuid"
)

type GetStatsByAccountsResponse struct {
	AccountId    uuid.UUID
	AccountLogin string
	ListCount    int64
	TaskAvg      float64
}
