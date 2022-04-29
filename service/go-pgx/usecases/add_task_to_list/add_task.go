package add_task_to_list

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"time"
)

type AddTask struct {
	Storage Storage
}

func (c *AddTask) Execute(request AddTaskRequest) (*AddTaskResponse, error) {
	id, err := uuid.NewV4()
	if err != nil {
		return nil, errors.Wrap(err, "failed to generate uuid")
	}

	taskResponse := &AddTaskResponse{
		Id:           id,
		Name:         request.Name,
		Description:  request.Description,
		ListId:       request.ListId,
		CreationDate: time.Now().UTC(),
	}

	err = c.Storage.AddTask(taskResponse)
	if err != nil {
		return nil, errors.Wrapf(err, "failed to create the task %s", request.Name)
	}

	return taskResponse, nil
}
