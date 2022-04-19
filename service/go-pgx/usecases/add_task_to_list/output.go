package add_task_to_list

import (
	"github.com/gofrs/uuid"
	"time"
)

type AddTaskResponse struct {
	Id           uuid.UUID
	Name         string
	Description  string
	CreationDate time.Time
	ListId       uuid.UUID
}
