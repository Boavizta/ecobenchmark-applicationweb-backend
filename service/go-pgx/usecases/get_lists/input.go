package get_lists

import (
	"github.com/gofrs/uuid"
)

type GetListsRequest struct {
	Page      int64
	AccountId uuid.UUID
}
