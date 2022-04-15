package add_account

import (
	"github.com/gofrs/uuid"
	"time"
)

type AddAccountResponse struct {
	Id           uuid.UUID
	Login        string
	CreationDate time.Time
}
