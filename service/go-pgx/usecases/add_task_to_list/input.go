package add_task_to_list

import (
	"github.com/gofrs/uuid"
)

type AddTaskRequest struct {
	Name        string
	Description string
	ListId      uuid.UUID
}
