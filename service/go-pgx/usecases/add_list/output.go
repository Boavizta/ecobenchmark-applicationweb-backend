package add_list

import (
	"github.com/gofrs/uuid"
	"time"
)

type AddListResponse struct {
	Id           uuid.UUID
	Name         string
	CreationDate time.Time
}
