package get_lists

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"go_pgx/endpoints"
	"go_pgx/usecases/get_lists"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
	"log"
)

func Controller(req *endpoints.GetListInput, storage get_lists.Storage) (*endpoints.GetListOutput, error) {
	useCase := get_lists.GetListsByAccountId{Storage: storage}

	accountId, err := uuid.FromString(req.AccountId)
	if err != nil {
		return nil, status.Error(codes.Aborted, "failed to handle the account id")
	}

	page := int64(0)
	if req.Page != nil {
		page = int64(*req.Page)
	}

	requestLists := get_lists.GetListsRequest{
		AccountId: accountId,
		Page:      page,
	}

	lists, err := useCase.Execute(requestLists)

	if err != nil {
		log.Printf("%+v", errors.Wrap(err, "failed to handle the request to get the lists"))
		return nil, status.Error(codes.Aborted, "failed to add the lists")
	}

	return &endpoints.GetListOutput{
		List: listToResponse(lists),
	}, nil
}

func listToResponse(tasks []get_lists.GetListsResponse) []*endpoints.List {
	response := make([]*endpoints.List, len(tasks))
	for i, l := range tasks {
		response[i] = &endpoints.List{
			Id:           l.Id.String(),
			CreationDate: timestamppb.New(l.CreationDate),
			Name:         l.Name,
			AccountId:    l.AccountId.String(),
			Tasks:        taskToResponse(l.Tasks),
		}
	}
	return response
}

func taskToResponse(tasks []get_lists.TasksResponse) []*endpoints.Task {
	response := make([]*endpoints.Task, len(tasks))
	for i, t := range tasks {
		response[i] = &endpoints.Task{
			Id:           t.Id.String(),
			CreationDate: timestamppb.New(t.CreationDate),
			Name:         t.Name,
			Description:  t.Description,
		}
	}
	return response
}
