package add_list

import (
	"github.com/gofrs/uuid"
)

type AddListRequest struct {
	Name      string
	AccountId uuid.UUID
}
