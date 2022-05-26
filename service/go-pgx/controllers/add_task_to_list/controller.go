package add_task_to_list

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"go_pgx/endpoints"
	"go_pgx/usecases/add_task_to_list"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
	"log"
)

func Controller(req *endpoints.AddTaskInput, storage add_task_to_list.Storage) (*endpoints.AddTaskOutput, error) {
	useCase := add_task_to_list.AddTask{Storage: storage}

	listId, err := uuid.FromString(req.ListId)
	if err != nil {
		return nil, status.Error(codes.Aborted, "failed to handle the listid")
	}

	requestTask := add_task_to_list.AddTaskRequest{
		Name:   req.Name,
		ListId: listId,
	}

	if (req.Description != nil) {
		requestTask.Description = *req.Description
	}

	createdTask, err := useCase.Execute(requestTask)

	if err != nil {
		log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the task"))
		return nil, status.Error(codes.Aborted, "failed to add the task")
	}

	return &endpoints.AddTaskOutput{Id: createdTask.Id.String(),
		ListId:       createdTask.ListId.String(),
		Name:         createdTask.Name,
		Description:  createdTask.Description,
		CreationDate: timestamppb.New(createdTask.CreationDate),
	}, nil
}
